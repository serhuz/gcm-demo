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

package com.example.gcm.health;

import com.codahale.metrics.health.HealthCheck;
import com.example.gcm.resources.gcm.BaseDownstreamMesage;
import com.example.gcm.resources.gcm.GCMResponse;
import com.google.common.collect.Lists;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * GCM api key health check.
 */
public class KeyHealthCheck extends HealthCheck {

    private final Client client;
    private final String gcmKey;
    private final List<String> ids;

    public KeyHealthCheck(Client client, String gcmKey) {
        this.client = client;
        this.gcmKey = gcmKey;

        ids = Lists.newArrayList("ABC");
    }

    @Override
    protected Result check() throws Exception {
        BaseDownstreamMesage message = new BaseDownstreamMesage(ids);

        WebTarget target = client.target("https://gcm-http.googleapis.com").path("gcm/send");

        try {
            GCMResponse response = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "key=" + gcmKey)
                    .post(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE), GCMResponse.class);
            return Result.healthy("Multicast ID: " + response.getMulticastId());
        } catch (WebApplicationException ex) {
            return Result.unhealthy("error " + String.valueOf(ex.getResponse().getStatus()));
        }
    }

}
