package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ClientEntity;

public interface ClientRepository {
    ClientEntity findClientById(Long idCliente);
    ClientEntity findClientByName(String nombre);
    ClientEntity findClientByEmail(String email);

    void saveClient(ClientEntity cliente);
    void updateClient(ClientEntity cliente);
    void deleteClientById(Long id);

    void logUserLogin(Long userId);

    void logUserRegistration(Long userId);

    void updateHomeLocation(Long clientId, Long locationId);

    Long findClientHomeLocation(Long clientId);
}
