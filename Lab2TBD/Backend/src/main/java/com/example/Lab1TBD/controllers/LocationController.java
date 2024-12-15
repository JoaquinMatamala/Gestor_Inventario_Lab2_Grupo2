package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.dto.GeoJsonDTO;
import com.example.Lab1TBD.persistence.entities.LocationEntity;
import com.example.Lab1TBD.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<String> saveLocation(@RequestBody GeoJsonDTO geoJson) {
        System.out.println("Propiedades recibidas: " + geoJson.getProperties());


        try {
            locationService.saveLocation(geoJson);
            return ResponseEntity.status(HttpStatus.CREATED).body("Ubicación guardada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la ubicación");
        }
    }

    @GetMapping("getLocation/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable Long id) {
        try {
            LocationEntity location = locationService.getLocationById(id);
            if (location == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ubicación no encontrada");
            }
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar la ubicación");
        }
    }

    @GetMapping("getType")
    public ResponseEntity<?> getLocationByType(@RequestParam String type) {
        try {
            LocationEntity location = locationService.getLocationByType(type);
            if (location == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron ubicaciones de ese tipo");
            }
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar ubicaciones");
        }
    }

    @GetMapping("/max-id")
    public ResponseEntity<LocationEntity> getLocationWithMaxId() {
        try {
            LocationEntity location = locationService.getLocationWithMaxId();
            return ResponseEntity.ok(location);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
