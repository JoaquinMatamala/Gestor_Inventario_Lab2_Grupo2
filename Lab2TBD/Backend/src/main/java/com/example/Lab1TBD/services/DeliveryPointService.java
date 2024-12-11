package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;
import com.example.Lab1TBD.persistence.repositories.DeliveryPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryPointService {

    @Autowired
    DeliveryPointRepository deliveryPointRepository;

    public DeliveryPointEntity getDeliveryPointById(Long id) {
        return deliveryPointRepository.findDeliveryPointById(id);
    }

    public List<DeliveryPointEntity> getAllDeliveryPointsByClientId(Long clientId) {
        return deliveryPointRepository.findAllDeliveryPointsByIdClient(clientId);
    }

    public DeliveryPointEntity getDeliveryPointByName(String name) {
        return deliveryPointRepository.findDeliveryPointByName(name);
    }

}
