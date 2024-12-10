package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.EstablishmentEntity;

import java.util.List;

public interface EstablishmentRepository {
    EstablishmentEntity findEstablishmentById(Long id);
    List<EstablishmentEntity> findAllEstablishments();
    EstablishmentEntity findEstablishmentByRegion(String region);
}
