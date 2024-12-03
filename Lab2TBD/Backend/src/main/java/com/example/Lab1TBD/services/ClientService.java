package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.ClientEntity;
import com.example.Lab1TBD.persistence.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Obtener cliente por ID
    public ClientEntity getClientById(Long id) {
        return clientRepository.findClientById(id);
    }

    public ClientEntity getClientByName(String name){
        return clientRepository.findClientByName(name);
    }

    // Obtener cliente por email
    public ClientEntity getClientByEmail(String email) {
        return clientRepository.findClientByEmail(email);
    }

    // Guardar un cliente
    public void saveClient(ClientEntity client) {
        clientRepository.saveClient(client);
    }

    // Actualizar un cliente por ID
    public void updateClient(ClientEntity client) {
        clientRepository.updateClient(client);
    }

    // Eliminar un cliente por ID
    public void deleteClient(Long id) {
        clientRepository.deleteClientById(id);
    }

    // Completar el registro del cliente (añadir dirección y teléfono)
    public ClientEntity completeRegistration(Long id, String address, String phoneNumber) {
        // Buscar cliente por ID
        ClientEntity client = clientRepository.findClientById(id);
        if (client == null) {
            return null; // Retornar null si no existe el cliente
        }

        // Añadir datos adicionales al cliente
        client.setAddress(address);
        client.setPhone_number(phoneNumber);

        // Guardar los cambios
        clientRepository.updateClient(client);

        return client;
    }

    public void logUserLogin(Long userId) {
        try {
            clientRepository.logUserLogin(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el login del usuario: " + e.getMessage(), e);
        }
    }

    public void logUserRegistration(Long userId) {
        try {
            clientRepository.logUserRegistration(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el log de registro: " + e.getMessage(), e);
        }
    }

}
