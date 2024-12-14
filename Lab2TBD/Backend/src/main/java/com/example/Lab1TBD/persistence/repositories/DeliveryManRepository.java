package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryManEntity;

public interface DeliveryManRepository{

    DeliveryManEntity findDeliveryManById(Long id);
    DeliveryManEntity findDeliveryManByName(String name);


    void saveDeliveryMan(DeliveryManEntity deliveryMan);
    void updateDeliveryMan(DeliveryManEntity deliveryMan);
    void deleteDeliveryManById(Long id);
    void logDeliveryManLogin(Long deliveryManId);
    void logDeliveryManRegistration(Long deliveryManId);
}
