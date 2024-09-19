package com.example.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordEncryptor {

    // Método para criptografar a senha
    public String encrypt(String password) {
        if (password == null) {
            throw new IllegalArgumentException("A senha não pode ser nula");
        }
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    // Método para verificar a senha com o hash
    public boolean verify(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            throw new IllegalArgumentException("Senhas não podem ser nulas");
        }
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
