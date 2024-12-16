package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.DeliveryManEntity;
import com.example.Lab1TBD.services.DeliveryManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveryman")
@CrossOrigin("*")
public class DeliveryManController {

    @Autowired
    DeliveryManService deliveryManService;

    // DEFAULT ------------------------------------------------------------------------------------
    @GetMapping("/")
    public ResponseEntity<List<DeliveryManEntity>> getAllDeliveryMen(){
        List<DeliveryManEntity> found = deliveryManService.getAllDeliveryMen();
        return found.isEmpty()? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(found);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<DeliveryManEntity> getDeliveryManById(@PathVariable Long id){
        DeliveryManEntity found = deliveryManService.getDeliveryManById(id);
        return found == null? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(found);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveDeliveryMan(@RequestParam Long userId, @RequestParam Long establishmentId){
        deliveryManService.saveDeliveryMan(userId,establishmentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateDeliveryMan(@RequestBody DeliveryManEntity deliveryMan){
        deliveryManService.updateDeliveryMan(deliveryMan);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDeliveryManById(@PathVariable Long id){
        deliveryManService.deleteDeliveryManById(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH -------------------------------------------------------------------------------------
    /*
    @GetMapping("/search/client/{clientId}")
    public ResponseEntity<List<DeliveryManEntity>> getDeliveryMenByClientId(@PathVariable Long clientId){
        List<DeliveryManEntity> found = deliveryManService.getDeliveryMenByClientId(clientId);
        return found.isEmpty()? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(found);
    }*/

    @GetMapping("/get/{clientId}")
    public ResponseEntity<DeliveryManEntity> getDeliveryManByClientId(@PathVariable Long clientId) {
        try {
            DeliveryManEntity deliveryMan = deliveryManService.getDeliveryManByClientId(clientId);
            if (deliveryMan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 si no se encuentra
            }
            return ResponseEntity.ok(deliveryMan); // Retorna 200 con la entidad
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 en caso de error
        }
    }



    @GetMapping("/search/establishment/{establishmentId}")
    public ResponseEntity<List<DeliveryManEntity>> getDeliveryMenByEstablishmentId(@PathVariable Long establishmentId){
        List<DeliveryManEntity> found = deliveryManService.getDeliveryMenByEstablishmentId(establishmentId);
        return found.isEmpty()? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(found);
    }
}
