package com.example.controllers;

import com.example.dtos.UserDTO;
import com.example.dtos.UserResponseDTO;
import com.example.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/access")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserAccessController {

    @Inject
    UserService userService;

    // Endpoint sem segurança para criar o primeiro usuário
    @POST
    @Path("/create-first-user")
    public Response createFirstUser(UserDTO userDTO) {
        try {
            UserResponseDTO user = userService.createUser(userDTO);
            return Response.status(Status.CREATED).entity(user).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
