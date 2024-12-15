package com.example.Lab1TBD.services;

import org.springframework.stereotype.Service;
import com.example.Lab1TBD.persistence.dto.GeoJsonDTO;
import java.util.List;
import java.util.Map;

@Service
public class GeoJsonService {

    public void processGeoJson(GeoJsonDTO geoJson) {
        // Obtener tipo de geometría
        String type = geoJson.getGeometry().getType();

        // Validar que sea un Point
        if (!"Point".equals(type)) {
            throw new IllegalArgumentException("Sólo se soporta el tipo 'Point'");
        }

        // Obtener coordenadas
        List<Double> coordinates = geoJson.getGeometry().getCoordinates();
        Double longitude = coordinates.get(0);
        Double latitude = coordinates.get(1);

        // Obtener propiedades adicionales
        Map<String, Object> properties = geoJson.getProperties();
        String locationType = (String) properties.get("location_type");

        // Procesar la información (puedes guardar en la base de datos o realizar otras acciones)
        System.out.printf("Procesando GeoJSON: Tipo: %s, Coordenadas: [%f, %f], Tipo de Localización: %s%n",
                type, longitude, latitude, locationType);
    }
}
