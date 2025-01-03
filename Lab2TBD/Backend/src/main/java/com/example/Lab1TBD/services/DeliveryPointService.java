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

    // DEFAULT ------------------------------------------------------------------------------------
    public List<DeliveryPointEntity> getAllDeliveryPoints(){
        return deliveryPointRepository.findAllDeliveryPoints();
    }

    public DeliveryPointEntity getDeliveryPointById(Long id) {
        return deliveryPointRepository.findDeliveryPointById(id);
    }

    public Long saveDeliveryPoint(DeliveryPointEntity deliveryPoint){
        return deliveryPointRepository.saveDeliveryPoint(deliveryPoint);
    }

    public void updateDeliveryPoint(DeliveryPointEntity deliveryPoint){
        deliveryPointRepository.updateDeliveryPoint(deliveryPoint);
    }

    public void deleteDeliveryPointById(Long id){
        deliveryPointRepository.deleteDeliveryPointById(id);
    }

    // SEARCH -------------------------------------------------------------------------------------
    public Long getLocationIdByDeliveryPointId(Long deliveryPointId) {
        return deliveryPointRepository.findLocationIdByDeliveryPointId(deliveryPointId);
    }

    public DeliveryPointEntity getDeliveryPointForClientAndLocation(Long clientId, Long locationId) {
        return deliveryPointRepository.findDeliveryPointForClientAndLocation(clientId, locationId);
    }

    public DeliveryPointEntity getDeliveryPointByName(String name) {
        return deliveryPointRepository.findDeliveryPointByName(name);
    }

    public List<DeliveryPointEntity> getAllDeliveryPointsByClientId(Long clientId) {
        return deliveryPointRepository.findAllDeliveryPointsByIdClient(clientId);
    }

    public List<DeliveryPointEntity> getDeliveryPointByDeliveryManId(Long deliveryManId) {
        return deliveryPointRepository.findDeliveryPointByDeliveryManId(deliveryManId);
    }

    // OTHER --------------------------------------------------------------------------------------
    public Long createDeliveryPoint(String name, Boolean status, String comment, Long locationPoint, Long clientId) {
        DeliveryPointEntity newPoint = new DeliveryPointEntity(
                null, // Se generara al guardarse.
                name,
                status,
                null,
                comment,
                locationPoint,
                null,
                clientId
        );

        // Guardar y retornar el ID generado
        return deliveryPointRepository.saveDeliveryPoint(newPoint);
    }

    public void updateDeliveryManId(Long deliveryPointId, Long deliveryManId) {
        deliveryPointRepository.updateDeliveryManId(deliveryPointId, deliveryManId);
    }

    public void updateStatusPoint(Long delivery_point_id, Boolean status){
        deliveryPointRepository.updateStatusPoint(delivery_point_id, status);
    }

    public Float findAllDeliveryPointsAVG(){
        return deliveryPointRepository.findAllDeliveryPointsAVG();
    }

    public void updateRating(Long delivery_point_id, Float rating){
        deliveryPointRepository.updateRating(delivery_point_id, rating);
    }
}
