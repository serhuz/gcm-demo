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

@JsonSnakeCase
@SuppressWarnings("unused")
public class GCMPushResult {

    private String messageId;
    private String registrationId;
    private String error;

    public GCMPushResult() {
    }

    @JsonProperty
    public String getMessageId() {
        return messageId;
    }

    @JsonProperty
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @JsonProperty
    public String getRegistrationId() {
        return registrationId;
    }

    @JsonProperty
    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @JsonProperty
    public String getError() {
        return error;
    }

    @JsonProperty
    public void setError(String error) {
        this.error = error;
    }
}
