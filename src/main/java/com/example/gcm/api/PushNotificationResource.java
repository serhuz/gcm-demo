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

package com.example.gcm.api;

import com.example.gcm.resources.gcm.GCMResponse;
import com.example.gcm.resources.gcm.Notification;
import com.example.gcm.resources.gcm.NotificationDownstreamMessage;
import com.example.gcm.db.AppId;
import com.example.gcm.db.AppIdDAO;
import com.example.gcm.resources.NotificationRequest;
import com.example.gcm.resources.responses.NotificationSendResponse;
import com.example.gcm.resources.responses.NotificationSendError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

@Api("GCM API")
@Path("/send")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PushNotificationResource {

    private static final Logger LOG = LoggerFactory.getLogger(PushNotificationResource.class);

    private final AppIdDAO dao;
    private Client client;
    private String gcmKey;
    private ObjectMapper mapper;

    public PushNotificationResource(AppIdDAO dao, Client client, String gcmKey) {
        this.dao = dao;
        this.client = client;
        this.gcmKey = gcmKey;

        mapper = new ObjectMapper();
    }

    @ApiOperation(value = "Send notification downstream message", response = NotificationSendResponse.class)
    @POST
    @Path("/notification")
    @UnitOfWork
    public NotificationSendResponse sendPushNotification(@QueryParam("dryRun") @ApiParam(required = true) boolean dryRun,
                                                       @ApiParam(required = true) @Valid NotificationRequest request) {

        List<AppId> ids = dao.findAll();
        List<String> registrationIds = new LinkedList<>();
        for (AppId id : ids) {
            registrationIds.add(id.getAppId());
        }

        NotificationDownstreamMessage message = new NotificationDownstreamMessage(
                registrationIds,
                request.getTimeToLive(),
                dryRun,
                new Notification(request)
        );

        WebTarget target = client.target("https://gcm-http.googleapis.com").path("gcm/send");

        try {
            GCMResponse response = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "key=" + gcmKey)
                    .post(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE), GCMResponse.class);

            int nSent = response.getSuccess();
            int nErrors = response.getFailure();

            try {
                LOG.info("GCM response: {}", mapper.writeValueAsString(response));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            return new NotificationSendResponse(nSent, nErrors);
        } catch (WebApplicationException ex) {
            return new NotificationSendError(0, ids.size(), ex.getResponse().getStatus());
        }
    }
}
