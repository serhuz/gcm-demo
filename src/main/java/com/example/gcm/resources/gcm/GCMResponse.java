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

import java.util.List;

/**
 * GCM response model.
 */
@JsonSnakeCase
@SuppressWarnings("unused")
public class GCMResponse {

    /**
     * Message ID
     */
    private String multicastId;

    /**
     * Number of successfully sent messages
     */
    private Integer success;

    /**
     * Number of undelivered messages
     */
    private Integer failure;

    /**
     * Used for compatibility purposes
     */
    private Integer canonicalIds;

    /**
     * List of detailed delivery descriptions
     */
    private List<GCMPushResult> results;

    /**
     * Default constructor.
     */
    public GCMResponse() {
        // Jackson nop
    }

    @JsonProperty
    public String getMulticastId() {
        return multicastId;
    }

    @JsonProperty
    public void setMulticastId(String multicastId) {
        this.multicastId = multicastId;
    }

    @JsonProperty
    public Integer getSuccess() {
        return success;
    }

    @JsonProperty
    public void setSuccess(Integer success) {
        this.success = success;
    }

    @JsonProperty
    public Integer getFailure() {
        return failure;
    }

    @JsonProperty
    public void setFailure(Integer failure) {
        this.failure = failure;
    }

    @JsonProperty
    public Integer getCanonicalIds() {
        return canonicalIds;
    }

    @JsonProperty
    public void setCanonicalIds(Integer canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    @JsonProperty
    public List<GCMPushResult> getResults() {
        return results;
    }

    @JsonProperty
    public void setResults(List<GCMPushResult> results) {
        this.results = results;
    }
}
