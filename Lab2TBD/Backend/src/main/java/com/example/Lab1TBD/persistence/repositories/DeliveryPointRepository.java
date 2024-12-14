package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;

import java.util.List;

public interface DeliveryPointRepository {
    DeliveryPointEntity findDeliveryPointById(Long id);
    DeliveryPointEntity findDeliveryPointByName(String name);
    List<DeliveryPointEntity> findAllDeliveryPointsByIdClient(Long id);
    void logDeliveryManRegistration(Long deliveryManId);
}
