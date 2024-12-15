package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;
import com.example.Lab1TBD.services.DeliveryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveryPoint")
@CrossOrigin("*")
public class DeliveryPointController {

    @Autowired
    private DeliveryPointService deliveryPointService;

    @GetMapping("/getallbyclient/{id}")
    public ResponseEntity<List<DeliveryPointEntity>> getAllDeliveryPointsByClient(@PathVariable Long id){
        return ResponseEntity.ok(deliveryPointService.getAllDeliveryPointsByClientId(id));
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<DeliveryPointEntity> searchDeliveryPointById(@PathVariable Long id){
        return ResponseEntity.ok(deliveryPointService.getDeliveryPointById(id));
    }

    @GetMapping("/search/name")
    public ResponseEntity<DeliveryPointEntity> searchDeliveryPointByName(@RequestParam String name){
        return ResponseEntity.ok(deliveryPointService.getDeliveryPointByName(name));
    }
    @PostMapping("/create")
    public ResponseEntity<String> createDeliveryPoint(@RequestBody DeliveryPointEntity deliveryPoint) {
        try {
            deliveryPointService.createDeliveryPoint(
                    deliveryPoint.getDelivery_point_name(),
                    deliveryPoint.getStatus_point(),
                    deliveryPoint.getComment(),
                    deliveryPoint.getDelivery_location_point(),
                    deliveryPoint.getClient_id()
            );
            return ResponseEntity.ok("Punto de entrega creado con éxito");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al crear el punto de entrega");
        }
    }




}
