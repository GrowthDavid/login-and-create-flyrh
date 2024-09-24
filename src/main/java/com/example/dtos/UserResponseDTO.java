package com.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String nome;
    private String email;
    private String cargo;

    private int telefone;
    private LocalDate atualizadoEm;
    private LocalDate criadoEm;
    private LocalDate desativadoEm;
    private boolean ativo;
}
