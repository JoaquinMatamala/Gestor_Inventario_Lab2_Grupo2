package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.dto.GeoJsonDTO;
import com.example.Lab1TBD.persistence.entities.LocationEntity;
import com.example.Lab1TBD.persistence.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public void saveLocation(GeoJsonDTO geoJson) {
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
        String locationType = (String) geoJson.getProperties().get("location_type");

        // Crear entidad LocationEntity
        LocationEntity location = new LocationEntity();
        location.setLocation_id(System.currentTimeMillis()); // Generar un ID único
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setPosition(String.format("POINT(%f %f)", longitude, latitude)); // Formato POINT para PostGIS
        location.setLocation_type(locationType);

        // Guardar en la base de datos
        locationRepository.saveLocation(location);
    }

    public LocationEntity getLocationById(Long id){
        return locationRepository.findLocationById(id);
    }

    public LocationEntity getLocationByType(String location_type){
        return locationRepository.findLocationByType(location_type);
    }
}
