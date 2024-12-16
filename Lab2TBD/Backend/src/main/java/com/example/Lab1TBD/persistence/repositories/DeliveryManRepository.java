package com.example.Lab1TBD.persistence.repositories;

import java.util.List;
import com.example.Lab1TBD.persistence.entities.DeliveryManEntity;

public interface DeliveryManRepository {

    DeliveryManEntity findDeliveryManById(Long id);
    List<DeliveryManEntity> findDeliveryManByClientId(String clientId);
    void saveDeliveryMan(DeliveryManEntity deliveryMan);
    void updateDeliveryMan(DeliveryManEntity deliveryMan);
    void deleteDeliveryManById(Long id);

}
