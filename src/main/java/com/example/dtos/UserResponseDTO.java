package com.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
    private String role;
}
