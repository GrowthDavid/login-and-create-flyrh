package com.example.repositories;

import com.example.models.User;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.types.ObjectId;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {

    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public User findById(ObjectId id) {
        return find("_id", id).firstResult();
    }
}
