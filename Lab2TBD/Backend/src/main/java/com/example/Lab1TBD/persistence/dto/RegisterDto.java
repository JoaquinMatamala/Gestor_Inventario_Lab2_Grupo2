package com.example.Lab1TBD.persistence.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private String phone_number;
    private String role;
}
