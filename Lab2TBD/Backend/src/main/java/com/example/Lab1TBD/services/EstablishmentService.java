package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.EstablishmentEntity;
import com.example.Lab1TBD.persistence.repositories.EstablishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablishmentService {

    @Autowired
    private EstablishmentRepository establishmentRepository;

    public EstablishmentEntity getEstablishmentById(Long id) {
        return establishmentRepository.findEstablishmentById(id);
    }

    public List<EstablishmentEntity> getAllEstablishments() {
        return establishmentRepository.findAllEstablishments();
    }

    public EstablishmentEntity getEstablishmentByRegion(String region) {
        return establishmentRepository.findEstablishmentByRegion(region);
    }
}
