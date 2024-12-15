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
            @RequestParam Long home_location,
            @RequestParam String phoneNumber) {
        // Llamar al servicio para completar el registro
        ClientEntity updatedClient = clientService.completeRegistration(id, home_location, phoneNumber);

        if (updatedClient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Cliente no encontrado
        }

        return ResponseEntity.ok(updatedClient); // Retornar cliente actualizado
    }
    @PostMapping("/assign-home-location")
    public ResponseEntity<?> assignHomeLocation(@RequestParam Long clientId, @RequestParam Long locationId) {
        try {
            clientService.assignHomeLocationToClient(clientId, locationId);
            return ResponseEntity.ok("Ubicación asignada exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asignar ubicación.");
        }
    }

    @GetMapping("/get-home-location/{id}")
    public ResponseEntity<?> getClientHomeLocation(@PathVariable Long id) {
        try {
            Long locationId = clientService.getClientHomeLocation(id);
            return ResponseEntity.ok(locationId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener ubicación.");
        }
    }

}
