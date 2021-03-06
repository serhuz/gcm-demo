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

package com.example.gcm.resources.responses;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Server response model.
 */
public class NotificationSendError extends NotificationSendResponse {

    /**
     * GCM error status
     */
    private int errorStatus;

    /**
     * Creates new instance of this class.
     *
     * @param nSent       {@inheritDoc}
     * @param nErrors     {@inheritDoc}
     * @param errorStatus {@inheritDoc}
     */
    @JsonCreator
    public NotificationSendError(@JacksonInject int nSent, @JacksonInject int nErrors, @JacksonInject int errorStatus) {
        super(nSent, nErrors);
        this.errorStatus = errorStatus;
    }

    @JsonProperty
    public int getErrorStatus() {
        return errorStatus;
    }
}
