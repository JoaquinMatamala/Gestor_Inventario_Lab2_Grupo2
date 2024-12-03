package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    private Long client_id;         // identificador unico del cliente
    private String client_name;   // nombre completo del cliente (nombres y apellidos)
    private String address;       //  direccion del cliente
    private String email;           // correo electronico del cliente
    private String password;           // clave unica
    private String phone_number;        // telefono del cliente
}

