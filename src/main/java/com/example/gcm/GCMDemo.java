/*
 * Copyright (c) 2015. Sergei Munovarov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gcm;

import com.example.gcm.api.AppServiceResource;
import com.example.gcm.api.PushNotificationResource;
import com.example.gcm.db.AppId;
import com.example.gcm.db.AppIdDAO;
import com.example.gcm.health.KeyHealthCheck;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.sun.akuma.Daemon;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.io.File;
import java.util.Properties;

/**
 * Main application class.
 *
 * {@link GCMDemo#initialize(Bootstrap)} method is used to initialize bundles and pass them to bootstrap.
 *
 * {@link GCMDemo#run(GCMDemoConfiguration, Environment)} method is used to register API resources, health checks
 * and perform other necessary instantiations.
 */
public class GCMDemo extends Application<GCMDemoConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(GCMDemo.class);

    protected static final String PROPERTY_DAEMON = "daemon";
    protected static final String PROPERTY_PIDFILE = "pidfile";
    protected static final String DEFAULT_PIDFILE = "/var/run/daemon.pid";

    protected GCMDemo daemonize() throws Exception {
        return daemonize(getPidfileOrDefault(System.getProperties()));
    }

    protected GCMDemo daemonize(final String pidfilePath) throws Exception {
        final Daemon daemon = new Daemon();
        if (daemon.isDaemonized()) {
            final File pidfile = new File(pidfilePath);
            daemon.init(pidfile.getAbsolutePath());

            if (pidfile.exists()) {
                pidfile.deleteOnExit();
            } else {
                LOG.warn("Unable to set automatic deletion of pidfile {}.", pidfile.getAbsolutePath());
            }
        } else if (shouldDaemonize(System.getProperties())) {
            daemon.daemonize();
            System.out.println("Launched daemon into background.");
            System.exit(0);
        }
        return this;
    }

    protected String getPidfileOrDefault(final Properties props) {
        return Optional.fromNullable(Strings.emptyToNull(props.getProperty(PROPERTY_PIDFILE)))
                .or(DEFAULT_PIDFILE);
    }

    protected boolean shouldDaemonize(final Properties props) {
        return Boolean.parseBoolean(props.getProperty(PROPERTY_DAEMON));
    }

    private final HibernateBundle<GCMDemoConfiguration> hibernateBundle =
            new HibernateBundle<GCMDemoConfiguration>(AppId.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(GCMDemoConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<GCMDemoConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(
                new SwaggerBundle<GCMDemoConfiguration>() {
                    @Override
                    public SwaggerBundleConfiguration getSwaggerBundleConfiguration(GCMDemoConfiguration configuration) {
                        return configuration.getSwaggerBundleConfiguration();
                    }
                }
        );
        bootstrap.addBundle(hibernateBundle);
    }

    public static void main(String[] args) throws Exception {
        new GCMDemo().daemonize().run(args);
    }

    @Override
    public void run(GCMDemoConfiguration gcmDemoConfiguration, Environment environment) throws Exception {
        AppIdDAO dao = new AppIdDAO(hibernateBundle.getSessionFactory());

        Client client = new JerseyClientBuilder(environment).using(gcmDemoConfiguration.getJerseyClient())
                .build("GCM demo");

        environment.jersey().register(new AppServiceResource(dao));
        environment.jersey().register(new PushNotificationResource(dao, client, gcmDemoConfiguration.getApiKey()));

        environment.healthChecks().register("apiKey", new KeyHealthCheck(client, gcmDemoConfiguration.getApiKey()));
    }
}
