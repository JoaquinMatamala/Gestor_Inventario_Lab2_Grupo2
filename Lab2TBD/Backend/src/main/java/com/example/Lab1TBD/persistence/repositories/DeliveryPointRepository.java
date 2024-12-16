package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;

import java.util.List;

public interface DeliveryPointRepository {
    // DEFAULT ------------------------------------------------------------------------------------
    List<DeliveryPointEntity> findAllDeliveryPoints();
    DeliveryPointEntity findDeliveryPointById(Long id);
    Long saveDeliveryPoint(DeliveryPointEntity deliveryPoint);
    void updateDeliveryPoint(DeliveryPointEntity deliveryPoint);
    void deleteDeliveryPointById(Long id);

    // SEARCH -------------------------------------------------------------------------------------
    List<DeliveryPointEntity> findAllDeliveryPointsByIdClient(Long id);
    List<DeliveryPointEntity> findDeliveryPointByDeliveryManId(Long deliveryManId);
    DeliveryPointEntity findDeliveryPointByName(String name);
    DeliveryPointEntity findDeliveryPointForClientAndLocation(Long clientId, Long locationId);
    Long findLocationIdByDeliveryPointId(Long deliveryPointId);

    // OTHER --------------------------------------------------------------------------------------

    // Actualiza el ID del deliveryman
    void updateDeliveryManId(Long delivery_point_id, Long deliveryman_id);

    // Actualizar el estado de un punto de entrega (activar/desactivar)
    void updateStatusPoint(Long delivery_point_id, Boolean status);

    // Obtener el promedio de valoración de todos los puntos de entrega
    Float findAllDeliveryPointsAVG();

    void updateRating(Long delivery_point_id, Float rating);
}
