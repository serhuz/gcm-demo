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

import com.example.gcm.db.AppId;
import com.example.gcm.db.AppIdDAO;
import com.example.gcm.resources.responses.CreateResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Application API resource.
 * <p>
 * Contains basic GCM app server methods to register and retrieve application IDs.
 */
@Api("Manage Application IDs")
@Path("/app")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AppServiceResource {

    private final AppIdDAO appIdDAO;

    public AppServiceResource(AppIdDAO appIdDAO) {
        this.appIdDAO = appIdDAO;
    }

    /**
     * Registers new application ID.
     *
     * @param appId Entity
     * @return ID
     */
    @ApiOperation(value = "Register new ID", response = CreateResponse.class)
    @POST
    @Path("/add")
    @UnitOfWork
    public CreateResponse createEntry(@Valid @ApiParam(required = true) AppId appId) {
        return new CreateResponse(appIdDAO.create(appId));
    }

    /**
     * Retrieves a list of registered application IDs.
     *
     * @return List of registered application IDs
     */
    @ApiOperation(value = "List registered IDs", responseContainer = "List", response = AppId.class)
    @GET
    @Path("/list")
    @UnitOfWork
    public List<AppId> listEntries() {
        return appIdDAO.findAll();
    }
}
