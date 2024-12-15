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
    public ResponseEntity<LocationEntity> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @GetMapping("getType")
    public ResponseEntity<LocationEntity> getLocationByType(@RequestParam String type) {
        return ResponseEntity.ok(locationService.getLocationByType(type));
    }
}
