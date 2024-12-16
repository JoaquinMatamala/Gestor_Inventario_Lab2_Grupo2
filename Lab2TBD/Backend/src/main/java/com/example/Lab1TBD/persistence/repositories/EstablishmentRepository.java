package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.EstablishmentEntity;

import java.util.List;

public interface EstablishmentRepository {
    // DEFAULT
    List<EstablishmentEntity> findAllEstablishments();
    EstablishmentEntity findEstablishmentById(Long id);
    void saveEstablishment(EstablishmentEntity establishment);
    void updateEstablishment(EstablishmentEntity establishment);
    void deleteEstablishmentById(Long id);

    // OTHER
    String findAddressByLocationId(Long locationId);
    EstablishmentEntity findEstablishmentByRegion(String region);
}
