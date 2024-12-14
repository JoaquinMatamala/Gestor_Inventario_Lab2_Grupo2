package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryManEntity {
    private Long deliveryMan_id;         //identificador unico del repartidor
    private String deliveryMan_name;     //nombre del repartidor
    private String deliveryMan_address;  //direccion de casa del repartidor
    private String deliveryMan_email;    //correo electronico del repartidor
    private String deliveryMan_password; //contraseña de entrada a la aplicacion
    private String deliveryMan_phone;    //telefono del repartidor
    private Integer establishment_id;   //identificador unico del establecimiento que trabaja el repartidor (FK)

}
