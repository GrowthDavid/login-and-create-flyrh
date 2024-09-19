package com.example.services;

import com.example.models.User;
import com.example.repositories.UserRepository;
import com.example.utils.PasswordEncryptor;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncryptor passwordEncryptor;

    public String authenticate(String email, String password) throws Exception {
        // Buscar o usuário pelo email
        User user = userRepository.findByEmail(email);

        // Verificar se o usuário existe
        if (user == null) {
            throw new Exception("Usuário não encontrado");
        }

        // Teste manual para verificar a senha
        System.out.println("--");
        System.out.println("Senha informada: " + password);
        System.out.println("Senha salva no banco: " + user.getPassword());

        boolean passwordMatch = passwordEncryptor.verify(password, user.getPassword());
        System.out.println("Senha confere: " + passwordMatch);  // Exibe se a senha está batendo com o hash no banco

        // Se a senha não bater, lançar exceção
        if (!passwordMatch) {
            throw new Exception("Senha incorreta");
        }

        // Se a senha for correta, gerar o token JWT
        Set<String> roles = new HashSet<>();
        roles.add(user.getRole());

        System.out.println("Log Antes do String Token" );

        try {
            String token = Jwt.issuer("http://localhost")
                    .subject(user.getId().toString())
                    .upn(user.getEmail())
                    .groups(roles)
                    .claim("name", user.getName())
                    .sign();

            System.out.println("Token JWT gerado: " + token);
            return token;
        } catch (Exception e) {
            System.out.println("Erro ao gerar o token JWT: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace completo para identificar o erro
            throw new RuntimeException("Erro ao gerar o token JWT", e);
        }

    }
}
