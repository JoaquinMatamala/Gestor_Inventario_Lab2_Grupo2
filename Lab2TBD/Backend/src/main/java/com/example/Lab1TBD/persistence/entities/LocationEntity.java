package com.example.Lab1TBD.persistence.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Point;


@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {
    private Long location_id;       // identificador unico de la localizacion
    private Double latitude;        // latitud de la local
    private Double longitude;       // longitud de la local
    private Point position;         // Point de la coordenada
    private String location_type;   // tipo de localizacion
}