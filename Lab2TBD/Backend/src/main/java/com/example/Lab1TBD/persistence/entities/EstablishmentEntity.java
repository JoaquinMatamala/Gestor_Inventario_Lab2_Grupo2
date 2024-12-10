package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablishmentEntity {
    private Long establishment_id;   //identificador del establecimiento
    private String establishment_data;  //data del establecimiento
    private String region_data;         //region del establecimiento
    private String latitude;            //latitud del establecimiento
    private String longitude;           //longitud del establecimiento
    private String position;            //coordenadas del establecimiento
}
