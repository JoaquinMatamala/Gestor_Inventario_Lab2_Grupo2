package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;
import com.example.Lab1TBD.services.DeliveryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveryPoint")
@CrossOrigin("*")
public class DeliveryPointController {

    @Autowired
    private DeliveryPointService deliveryPointService;

    @GetMapping("/getallbyclient/{id}")
    public ResponseEntity<List<DeliveryPointEntity>> getAllDeliveryPointsByClient(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryPointService.getAllDeliveryPointsByClientId(id));
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<DeliveryPointEntity> searchDeliveryPointById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryPointService.getDeliveryPointById(id));
    }

    @GetMapping("/search/name")
    public ResponseEntity<DeliveryPointEntity> searchDeliveryPointByName(@RequestParam String name) {
        return ResponseEntity.ok(deliveryPointService.getDeliveryPointByName(name));
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createDeliveryPoint(@RequestBody DeliveryPointEntity deliveryPoint) {
        try {
            Long generatedId = deliveryPointService.createDeliveryPoint(
                    deliveryPoint.getDelivery_point_name(),
                    deliveryPoint.getStatus_point(),
                    deliveryPoint.getComment(),
                    deliveryPoint.getDelivery_location_point(),
                    deliveryPoint.getClient_id()
            );
            return ResponseEntity.ok(generatedId); // Retornar solo el ID generado
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search-existing")
    public ResponseEntity<DeliveryPointEntity> searchExistingDeliveryPoint(
            @RequestParam Long clientId,
            @RequestParam Long locationId) {
        try {
            DeliveryPointEntity deliveryPoint = deliveryPointService.getDeliveryPointForClientAndLocation(clientId, locationId);
            if (deliveryPoint == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(deliveryPoint);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // GETTERS ------------------------------------------------------------------------------------

    @GetMapping("/get-location-id/{id}")
    public ResponseEntity<Long> getLocationIdByDeliveryPointId(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryPointService.getLocationIdByDeliveryPointId(id));
    }

    @PostMapping("/update-deliveryman")
    public ResponseEntity<Void> updateDeliveryManId(
            @RequestParam Long deliveryPointId,
            @RequestParam Long deliveryManId) {
        try {
            deliveryPointService.updateDeliveryManId(deliveryPointId, deliveryManId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search/deliveryman/{id}")
    public ResponseEntity<List<DeliveryPointEntity>> searchDeliveryPointByDeliveryManId(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryPointService.getDeliveryPointByDeliveryManId(id));
    }

    // UPDATERS ------------------------------------------------------------------------------------
    @PutMapping("/update-rating")
    public ResponseEntity<Void> updateRating(
            @RequestParam Long deliveryPointId,
            @RequestParam Float rating) {
        try {
            deliveryPointService.updateRating(deliveryPointId, rating);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
