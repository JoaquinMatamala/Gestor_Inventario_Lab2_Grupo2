package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.EstablishmentEntity;
import com.example.Lab1TBD.services.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/establishment")
@CrossOrigin("*")
public class EstablishmentController {

    @Autowired
    EstablishmentService establishmentService;

    @GetMapping("/getall")
    public ResponseEntity<List<EstablishmentEntity>> getAllEstablishment(){
        return ResponseEntity.ok(establishmentService.getAllEstablishments());
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<EstablishmentEntity> getEstablishmentID(@PathVariable Long id) {
        return ResponseEntity.ok(establishmentService.getEstablishmentById(id));
    }

    @GetMapping("/search/region")
    public ResponseEntity<EstablishmentEntity> getEstablishmentRegion(@RequestParam String region) {
        return ResponseEntity.ok(establishmentService.getEstablishmentByRegion(region));
    }

    @PostMapping("/build")
    public ResponseEntity<Void> buildEstablishment(@RequestBody EstablishmentEntity establishment) {
        establishmentService.saveEstablishment(establishment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getdetails/{id}")
    public ResponseEntity<String> getAddressForLocation(@PathVariable Long id) {
        return ResponseEntity.ok(establishmentService.getAddressForLocation(id));
    }

}
