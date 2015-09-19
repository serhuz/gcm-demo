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

package com.example.gcm.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NotificationRequest {

    @ApiModelProperty(allowableValues = "0 - 2419200", required = true)
    @NotNull
    @Min(0)
    @Max(2419200)
    private Integer timeToLive;

    @ApiModelProperty(value = "Notification title", required = true)
    @NotBlank
    private String title;

    @ApiModelProperty(value = "Notification body", required = true)
    @NotBlank
    private String body;

    @ApiModelProperty(value = "Icon resource", required = true, example = "ic_launcher")
    private String icon;

    public NotificationRequest() {
        // Jackson nop
    }

    public NotificationRequest(Integer timeToLive, String title, String body, String icon) {
        this.timeToLive = timeToLive;
        this.title = title;
        this.body = body;
        this.icon = icon;
    }

    @JsonProperty
    public Integer getTimeToLive() {
        return timeToLive;
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
