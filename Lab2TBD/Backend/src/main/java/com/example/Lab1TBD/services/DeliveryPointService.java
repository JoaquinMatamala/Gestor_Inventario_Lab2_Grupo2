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

    public Long createDeliveryPoint(String name, Boolean status, String comment, Long locationPoint, Long clientId) {
        DeliveryPointEntity deliveryPoint = new DeliveryPointEntity();
        deliveryPoint.setDelivery_point_name(name);
        deliveryPoint.setStatus_point(status);
        deliveryPoint.setComment(comment);
        deliveryPoint.setDelivery_location_point(locationPoint);
        deliveryPoint.setClient_id(clientId);

        // Guardar y retornar el ID generado
        return deliveryPointRepository.saveDeliveryPoint(deliveryPoint);
    }


    public DeliveryPointEntity findDeliveryPointForClientAndLocation(Long clientId, Long locationId) {
        return deliveryPointRepository.findDeliveryPointForClientAndLocation(clientId, locationId);
    }
}
