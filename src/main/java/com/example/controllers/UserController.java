package com.example.controllers;

import com.example.dtos.UserDTO;
import com.example.dtos.UserResponseDTO;
import com.example.exceptions.EmailAlreadyInUseException;
import com.example.services.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.NotFoundException;
import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    @Inject
    UserService userService;

    @POST
    @RolesAllowed("admin")
    public Response createUser(UserDTO userDTO) {
        try {
            UserResponseDTO user = userService.createUser(userDTO);
            return Response.status(Status.CREATED).entity(user).build();
        } catch (EmailAlreadyInUseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @RolesAllowed("admin")
    public List<UserResponseDTO> listUsers() {
        return userService.listUsers();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response updateUser(@PathParam("id") String id, UserDTO userDTO) {
        try {
            userService.updateUser(id, userDTO);
            return Response.ok().build();
        } catch (EmailAlreadyInUseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response deleteUser(@PathParam("id") String id) {
        try {
            userService.deleteUser(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response patchUser(@PathParam("id") String id, UserDTO userDTO) {
        try {
            userService.updateUser(id, userDTO);
            return Response.ok().build();
        } catch (EmailAlreadyInUseException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
