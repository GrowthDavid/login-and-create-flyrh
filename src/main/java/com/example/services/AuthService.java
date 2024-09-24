package com.example.services;

import com.example.models.User;
import com.example.repositories.UserRepository;
import com.example.utils.PasswordEncryptor;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncryptor passwordEncryptor;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public String authenticate(String email, String password) throws Exception {
        // Buscar o usuário pelo email
        User user = userRepository.findByEmail(email);

        // Verificar se o usuário existe
        if (user == null) {
            throw new Exception("Usuário não encontrado");
        }

        // Verificar se o usuário está ativo
        if (!user.isAtivo()) {
            throw new Exception("Usuário inativo");
        }

        // Verificar a senha
        boolean passwordMatch = passwordEncryptor.verify(password, user.getSenha());

        // Se a senha não bater, lançar exceção
        if (!passwordMatch) {
            throw new Exception("Senha incorreta");
        }

        // Se a senha for correta, gerar o token JWT
        Set<String> roles = new HashSet<>();
        roles.add(user.getCargo());

        try {
            String token = Jwt.issuer("http://localhost")
                    .subject(user.getId().toString())
                    .upn(user.getEmail())
                    .groups(roles)
                    .claim("name", user.getNome())
                    .sign();

            LOGGER.info("Token JWT gerado com sucesso para o usuário: {}", user.getEmail());
            return token;
        } catch (Exception e) {
            LOGGER.error("Erro ao gerar o token JWT: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao gerar o token JWT", e);
        }
    }
}
