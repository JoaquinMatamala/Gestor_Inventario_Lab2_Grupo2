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
    public void createDeliveryPoint(String name, Boolean status, String comment, Long locationPoint, Long clientId) {
        DeliveryPointEntity deliveryPoint = new DeliveryPointEntity();
        deliveryPoint.setDelivery_point_name(name);
        deliveryPoint.setStatus_point(status);
        deliveryPoint.setRating(null); // Rating inicial en null
        deliveryPoint.setComment(comment);
        deliveryPoint.setDelivery_location_point(locationPoint);
        deliveryPoint.setDeliveryman_id(null); // Deliveryman asignado después
        deliveryPoint.setClient_id(clientId);

        deliveryPointRepository.saveDeliveryPoint(deliveryPoint);
    }


}
