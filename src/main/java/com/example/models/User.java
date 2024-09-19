package com.example.models;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "users")
public class User {

    private ObjectId id;
    private String name;
    private String email;
    private String password;
    private String role;
}
