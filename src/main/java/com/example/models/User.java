package com.example.models;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "users")
public class User {

    private ObjectId id;
    private String nome;
    private String email;
    private String senha;
    private String cargo;
    private int telefone;                 // Novo campo: número de telefone
    private LocalDate atualizadoEm;    // Novo campo: data de inclusão
    private LocalDate criadoEm; // Novo campo: data de atualização
    private LocalDate desativadoEm; // Novo campo: data de desativação
    private boolean ativo;            // Novo campo: status de ativo/inativo

}
