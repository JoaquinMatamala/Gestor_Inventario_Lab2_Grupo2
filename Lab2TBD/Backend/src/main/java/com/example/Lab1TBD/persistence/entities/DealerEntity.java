package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealerEntity {
    private Long dealer_id;         //identificador unico del repartidor
    private String dealer_name;     //nombre del repartidor
    private String dealer_address;  //direccion de casa del repartidor
    private String dealer_email;    //correo electronico del repartidor
    private String dealer_password; //contraseña de entrada a la aplicacion
    private String dealer_phone;    //telefono del repartidor
    private Integer establishment_id;   //identificador unico del establecimiento que trabaja el repartidor (FK)

}
