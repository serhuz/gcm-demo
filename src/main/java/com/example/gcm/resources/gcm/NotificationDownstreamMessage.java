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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Downstream message model.
 */
@JsonSnakeCase
public class NotificationDownstreamMessage extends BaseDownstreamMesage {

    /**
     * A maximum time which message will be cached on GCM server.
     * If set to 0, GCM will try its best to deliver message now.
     */
    @NotNull
    private int timeToLive;

    /**
     * Specifies delivery mode.
     * If set to false, message won't be sent to actual devices.
     * Can be used for testing purposes.
     */
    @NotNull
    private boolean dryRun;

    /**
     * Notification message contents.
     */
    @Valid
    @NotNull
    private Notification notification;

    /**
     * Default constructor.
     */
    public NotificationDownstreamMessage() {
        // Jackson nop
    }

    /**
     * Constructs new instance of this class.
     *
     * @param registrationIds {@inheritDoc}
     * @param timeToLive      {@inheritDoc}
     * @param dryRun          {@inheritDoc}
     * @param notification    {@inheritDoc}
     */
    public NotificationDownstreamMessage(List<String> registrationIds,
                                         int timeToLive,
                                         boolean dryRun,
                                         Notification notification) {
        super(registrationIds);
        this.timeToLive = timeToLive;
        this.dryRun = dryRun;
        this.notification = notification;
    }

    @JsonProperty
    public int getTimeToLive() {
        return timeToLive;
    }

    @JsonProperty
    public boolean getDryRun() {
        return dryRun;
    }

    @JsonProperty
    public Notification getNotification() {
        return notification;
    }
}
