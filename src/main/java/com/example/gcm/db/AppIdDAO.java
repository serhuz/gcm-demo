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

public class AppIdDAO extends AbstractDAO<AppId> {

    public AppIdDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public long create(AppId appId) {
        return persist(appId).getId();
    }

    public List<AppId> findAll() {
        return list(namedQuery("com.example.gcm.db.AppId.findAll"));
    }

    public void remove(AppId appId) {
        currentSession().delete(appId);
    }
}
