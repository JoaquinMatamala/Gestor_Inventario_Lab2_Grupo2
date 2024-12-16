package com.example.Lab1TBD.persistence.repositories;

import java.util.List;
import com.example.Lab1TBD.persistence.entities.DeliveryManEntity;

public interface DeliveryManRepository {
    List<DeliveryManEntity> findAllDeliveryMen();
    DeliveryManEntity findDeliveryManById(Long id);
    void saveDeliveryMan(Long userId, Long establishmentId);
    void updateDeliveryMan(DeliveryManEntity deliveryMan);
    void deleteDeliveryManById(Long id);

    // SEARCH
    DeliveryManEntity findDeliveryManByClientId(Long clientId);
    List<DeliveryManEntity> findDeliveryManByEstablishmentId(Long establishmentId);
}
