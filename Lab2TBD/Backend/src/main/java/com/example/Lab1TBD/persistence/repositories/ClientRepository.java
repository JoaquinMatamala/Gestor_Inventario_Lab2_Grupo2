package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.ClientEntity;

public interface ClientRepository {
    ClientEntity findClientById(Long idClient);
    ClientEntity findClientByName(String name);
    ClientEntity findClientByEmail(String email);

    void saveClient(ClientEntity client);
    void updateClient(ClientEntity client);
    void deleteClientById(Long id);

    void logUserLogin(Long userId);
    void logUserRegistration(Long userId);

    void updateHomeLocation(Long clientId, Long locationId);

}
