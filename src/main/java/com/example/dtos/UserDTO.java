package com.example.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private String nome;
    private String email;
    private String senha;
    private String cargo;
    private int telefone;
    private LocalDate atualizadoEm;
    private LocalDate criadoEm;
    private LocalDate desativadoEm;
    private boolean ativo;
}

