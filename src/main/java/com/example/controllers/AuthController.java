package com.example.controllers;

import com.example.dtos.LoginDTO;
import com.example.services.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(LoginDTO loginDTO) {
        try {
            // Autenticar o usuário e gerar o token JWT
            String token = authService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());

            // Retornar o token no corpo da resposta e no cabeçalho Authorization (opcional)
            return Response.ok()
                    .header("Authorization", "Bearer " + token) // Opcional
                    .entity("{\"token\":\"" + token + "\"}")
                    .build();
        } catch (Exception e) {
            // Log da exceção (para debug)
            System.out.println("Erro na autenticação: " + e.getMessage());

            // Retorna a mensagem genérica de credenciais inválidas
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\":\"Credenciais inválidas\"}")
                    .build();
        }
    }
}
