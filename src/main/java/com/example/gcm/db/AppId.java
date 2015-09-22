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

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

/**
 * Hibernate entity class.
 */
@ApiModel
@Entity
@Table(name = "ids")
@NamedQueries({
        @NamedQuery(
                name = "com.example.gcm.db.AppId.findAll",
                query = "SELECT p FROM AppId p"
        )
})
public class AppId {

    /**
     * ID field.
     */
    @ApiModelProperty(dataType = "long")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Application ID assigned by GCM.
     */
    @ApiModelProperty(example = "ABC")
    @Column(name = "appId", nullable = false)
    private String appId;

    /**
     * Default constructor.
     */
    public AppId() {
    }

    /**
     * Creates entity with {@code id} & {@code appId} fields.
     *
     * @param id    ID
     * @param appId Application ID
     */
    public AppId(long id, String appId) {
        this.id = id;
        this.appId = appId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AppId)) return false;

        final AppId that = (AppId) obj;

        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.appId, that.appId);
    }
}
