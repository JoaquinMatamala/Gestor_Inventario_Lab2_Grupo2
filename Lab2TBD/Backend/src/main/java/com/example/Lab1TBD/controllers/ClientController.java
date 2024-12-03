package com.example.Lab1TBD.controllers;


import com.example.Lab1TBD.persistence.entities.ClientEntity;
import com.example.Lab1TBD.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@CrossOrigin("*")
public class ClientController {
    
    @Autowired
    ClientService clientService;

    @PutMapping("/complete-registration/{id}")
    public ResponseEntity<ClientEntity> completeRegistration(
            @PathVariable Long id,
            @RequestParam String address,
            @RequestParam String phoneNumber) {
        // Llamar al servicio para completar el registro
        ClientEntity updatedClient = clientService.completeRegistration(id, address, phoneNumber);

        if (updatedClient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Cliente no encontrado
        }

        return ResponseEntity.ok(updatedClient); // Retornar cliente actualizado
    }

}
