package com.example.services;

import com.example.dtos.UserDTO;
import com.example.dtos.UserResponseDTO;
import com.example.exceptions.EmailAlreadyInUseException;
import com.example.models.User;
import com.example.repositories.UserRepository;
import com.example.utils.PasswordEncryptor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncryptor passwordEncryptor;

    @Transactional
    public UserResponseDTO createUser(UserDTO userDTO) throws EmailAlreadyInUseException {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new EmailAlreadyInUseException("E-mail já está em uso.");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncryptor.encrypt(userDTO.getPassword()));
        user.setRole(userDTO.getRole());

        userRepository.persist(user);

        return new UserResponseDTO(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public List<UserResponseDTO> listUsers() {
        return userRepository.listAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getId().toString(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(String id, UserDTO userDTO) throws EmailAlreadyInUseException {
        User user = userRepository.findById(new ObjectId(id));
        if (user == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        // Atualizar o nome se fornecido
        if (userDTO.getName() != null && !userDTO.getName().isEmpty()) {
            user.setName(userDTO.getName());
        }

        // Atualizar o email se fornecido
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            User existingUser = userRepository.findByEmail(userDTO.getEmail());
            if (existingUser != null && !existingUser.getId().equals(user.getId())) {
                throw new EmailAlreadyInUseException("E-mail já está em uso.");
            }
            user.setEmail(userDTO.getEmail());
        }

        // Atualizar o role se fornecido
        if (userDTO.getRole() != null && !userDTO.getRole().isEmpty()) {
            user.setRole(userDTO.getRole());
        }

        // Atualizar a senha se fornecida
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncryptor.encrypt(userDTO.getPassword()));
        }

        userRepository.update(user);
    }

    @Transactional
    public void deleteUser(String id) {
        boolean deleted = userRepository.deleteById(new ObjectId(id));
        if (!deleted) {
            throw new NotFoundException("Usuário não encontrado");
        }
    }
}
