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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());
        user.setSenha(passwordEncryptor.encrypt(userDTO.getSenha()));
        user.setCargo(userDTO.getCargo());

        // Definir campos adicionais
        user.setTelefone(userDTO.getTelefone());
        user.setCriadoEm(LocalDate.from(LocalDateTime.now())); // Definindo a data de criação
        user.setAtualizadoEm(LocalDate.from(LocalDateTime.now())); // Definindo a data de atualização
        user.setDesativadoEm(null); // DeactivatedAt começa como null
        user.setAtivo(true); // Inicialmente, o usuário está ativo

        userRepository.persist(user);

        return new UserResponseDTO(
                user.getId().toString(),
                user.getNome(),
                user.getEmail(),
                user.getCargo(),
                user.getTelefone(),
                user.getAtualizadoEm(),
                user.getCriadoEm(),
                user.getDesativadoEm(),
                user.isAtivo()
        );
    }

    public List<UserResponseDTO> listUsers() {
        return userRepository.listAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getId().toString(),
                        user.getNome(),
                        user.getEmail(),
                        user.getCargo(),
                        user.getTelefone(),
                        user.getAtualizadoEm(),
                        user.getCriadoEm(),
                        user.getDesativadoEm(),
                        user.isAtivo()
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
        if (userDTO.getNome() != null && !userDTO.getNome().isEmpty()) {
            user.setNome(userDTO.getNome());
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
        if (userDTO.getCargo() != null && !userDTO.getCargo().isEmpty()) {
            user.setCargo(userDTO.getCargo());
        }

        // Atualizar a senha se fornecida
        if (userDTO.getSenha() != null && !userDTO.getSenha().isEmpty()) {
            user.setSenha(passwordEncryptor.encrypt(userDTO.getSenha()));
        }

        // Atualizar o telefone se fornecido
        if (userDTO.getTelefone() != 0) {
            user.setTelefone(userDTO.getTelefone());
        }

        // Atualizar a data de atualização
        user.setAtualizadoEm(LocalDate.from(LocalDateTime.now()));

        // Verificar se o usuário foi desativado
        if (!userDTO.isAtivo() && user.isAtivo()) {
            user.setDesativadoEm(LocalDate.from(LocalDateTime.now())); // Definir a data de desativação
            user.setAtivo(false);
        } else if (userDTO.isAtivo() && !user.isAtivo()) {
            // Reativar o usuário
            user.setDesativadoEm(null); // Remover a data de desativação
            user.setAtivo(true);
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
