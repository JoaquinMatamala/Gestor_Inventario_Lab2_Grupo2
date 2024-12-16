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

    // DEFAULT ------------------------------------------------------------------------------------
    public List<LocationEntity> getAllLocations(){
        return locationRepository.findAllLocations();
    }

    public LocationEntity getLocationById(Long id) {
        return locationRepository.findLocationById(id);
    }

    public void saveLocation(GeoJsonDTO geoJson) {
        String type = geoJson.getGeometry().getType();
        if (!"Point".equals(type)) {
            throw new IllegalArgumentException("Sólo se soporta el tipo 'Point'");
        }

        // Obtener las coordenadas
        List<Double> coordinates = geoJson.getGeometry().getCoordinates();
        Double longitude = coordinates.get(0);
        Double latitude = coordinates.get(1);

        // Obtener las propiedades adicionales
        String locationType = (String) geoJson.getProperties().get("location_type");
        String address = (String) geoJson.getProperties().get("address");

        // Crear la entidad
        LocationEntity location = new LocationEntity();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setPosition(String.format("POINT(%f %f)", longitude, latitude)); // Convertir a WKT
        location.setAddress(address);
        location.setLocation_type(locationType);

        // Guardar en el repositorio
        locationRepository.saveLocation(location);
    }

    public void deleteLocationById(Long location_id){
        locationRepository.deleteLocationById(location_id);
    }

    public LocationEntity getLocationWithMaxId() {
        return locationRepository.findLocationWithMaxId();
    }


    public List<LocationEntity> getLocationByType(String location_type) {
        return locationRepository.findLocationByType(location_type);
    }

    public List<LocationEntity> getLocationByAddress(String address){
        return locationRepository.findLocationByAddress(address);
    }
}

