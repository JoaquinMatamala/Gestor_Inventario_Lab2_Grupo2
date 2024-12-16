package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.DeliveryManEntity;
import com.example.Lab1TBD.persistence.repositories.DeliveryManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryManService {

    @Autowired
    private DeliveryManRepository deliveryManRepository;

    public DeliveryManEntity getDeliveryManById(Long id) {
        return deliveryManRepository.findDeliveryManById(id);
    }

    public List<DeliveryManEntity> getDeliveryMenByClientId(String clientId) {
        return deliveryManRepository.findDeliveryManByClientId(clientId);
    }

    public void saveDeliveryMan(DeliveryManEntity deliveryMan) {
        deliveryManRepository.saveDeliveryMan(deliveryMan);
    }

    public void updateDeliveryMan(DeliveryManEntity deliveryMan) {
        deliveryManRepository.updateDeliveryMan(deliveryMan);
    }

    public void deleteDeliveryManById(Long id) {
        deliveryManRepository.deleteDeliveryManById(id);
    }
}
