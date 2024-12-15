package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    private Long client_id;       // Unique ID
    private String client_name;   // Client name
    private String email;         // Client email
    private String password;      // Password
    private String phone_number;  // Phone number
    private Long home_location;   // Address or location (FK)
    private String role;          // Role (e.g., "ADMIN", "USER")
}
