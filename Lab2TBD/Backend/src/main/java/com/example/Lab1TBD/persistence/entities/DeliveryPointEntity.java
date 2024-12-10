package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPointEntity {
    private Long delivery_point_id;        //identificador del punto de entrega
    private String delivery_point_name;   //Nombre del punto de entrega (ej: casa,trabajo,casa del arbol, etc)
    private String latitude;            //latitud del punto de entrega
    private String longitude;           //longitud del punto de entrega
    private String position;            //coordenadas del punto de entrega
    private Boolean status_point;        //punto de entrega seleccionado por el cliente (0: no seleccionado;1:seleccionado por cliente)
    private Integer rating;             //media que usa el dealer para medir su peligrosidad de entrega
    private Integer client_id;          //id del cliente asociado al punto de entrega (FK)
    //la media esta bajo propuesta de cambio ya que no se si hacer una media o solo presentar
    //una cantidad de valoracion mas usada por los dealer (ej: 1*: 100,2*: 20,3*: 10; por tanto valor = 1*)
}
