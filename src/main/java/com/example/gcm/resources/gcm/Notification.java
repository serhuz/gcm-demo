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

package com.example.gcm.resources.gcm;

import com.example.gcm.resources.NotificationRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Notification downstream message model.
 */
@JsonSnakeCase
public class Notification {

    /**
     * Notification title.
     */
    @NotBlank
    private String title;

    /**
     * Notification contents.
     */
    @NotBlank
    private String body;

    /**
     * Drawable resource available in Android app.
     */
    @NotBlank
    private String icon;

    /**
     * Default constructor.
     */
    public Notification() {
        // Jackson nop
    }

    /**
     * Creates new instance of this class.
     *
     * @param title {@inheritDoc}
     * @param body  {@inheritDoc}
     * @param icon  {@inheritDoc}
     */
    public Notification(String title, String body, String icon) {
        this.title = title;
        this.body = body;
        this.icon = icon;
    }

    /**
     * Creates new instance of this class using {@link NotificationRequest} instance.
     *
     * @param request {@link NotificationRequest} instance
     */
    public Notification(NotificationRequest request) {
        this.title = request.getTitle();
        this.body = request.getBody();
        this.icon = request.getIcon();
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public String getBody() {
        return body;
    }

    @JsonProperty
    public String getIcon() {
        return icon;
    }
}
