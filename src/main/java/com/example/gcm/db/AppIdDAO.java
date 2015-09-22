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

package com.example.gcm.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * A Hibernate DAO class.
 * <p>
 * Incapsulates specific CRUD features.
 */
public class AppIdDAO extends AbstractDAO<AppId> {

    public AppIdDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Either saves or updates given entity instance.
     *
     * @param appId Entity
     * @return assigned ID
     */
    public long create(AppId appId) {
        return persist(appId).getId();
    }

    /**
     * Retrieves a list of persisted application IDs assigned by GCM.
     *
     * @return list of {@link AppId} instances
     */
    public List<AppId> findAll() {
        return list(namedQuery("com.example.gcm.db.AppId.findAll"));
    }

    /**
     * Removes given entity from DB.
     *
     * @param appId Entity
     */
    public void remove(AppId appId) {
        currentSession().delete(appId);
    }
}
