package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.EstablishmentEntity;

import java.util.List;

public interface EstablishmentRepository {
    List<EstablishmentEntity> findAllEstablishments();
    EstablishmentEntity findEstablishmentById(Long id);
    EstablishmentEntity findEstablishmentByRegion(String region);
    void saveEstablishment(EstablishmentEntity establishment);

    String getAddressByLocationId(Long locationId);
}
