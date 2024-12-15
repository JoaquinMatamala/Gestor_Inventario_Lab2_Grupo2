package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;

import java.util.List;

public interface DeliveryPointRepository {
    DeliveryPointEntity findDeliveryPointById(Long id);
    DeliveryPointEntity findDeliveryPointByName(String name);
    List<DeliveryPointEntity> findAllDeliveryPointsByIdClient(Long id);

    // Obtener el promedio de valoración de todos los puntos de entrega
    Float findAllDeliveryPointsAVG();

    // Actualizar el estado de un punto de entrega (activar/desactivar)
    void updateStatusPoint(Long delivery_point_id, Boolean status);

    Long saveDeliveryPoint(DeliveryPointEntity deliveryPoint);

    DeliveryPointEntity findDeliveryPointForClientAndLocation(Long clientId, Long locationId);
}
