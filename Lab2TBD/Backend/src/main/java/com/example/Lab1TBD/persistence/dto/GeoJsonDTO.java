package com.example.Lab1TBD.persistence.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoJsonDTO {
    private Geometry geometry; // Representa la geometría (Point, LineString, Polygon, etc.)
    private Map<String, Object> properties; // Propiedades adicionales del GeoJSON

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Geometry {
        private String type; // Tipo de geometría (ej: "Point", "LineString", etc.)
        private List<Double> coordinates; // Coordenadas de la geometría
    }
}

