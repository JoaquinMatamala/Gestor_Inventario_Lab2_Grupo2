package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;

public interface DeliveryPointRepository {
    DeliveryPointEntity findDeliveryPointById(Long id);
    DeliveryPointEntity findDeliveryPointByName(String name);
}
