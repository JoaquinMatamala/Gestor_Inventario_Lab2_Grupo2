package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderEntity;

import java.util.List;

public interface OrderRepository {
    List<OrderEntity> findAllOrders();
    OrderEntity findByOrderId(Long order_id);
    Long saveOrder(OrderEntity order);
    void updateOrder(OrderEntity order);
    void deleteOrderById(Long order_id);

    List<OrderEntity> findByClientId(Long clientId);
    List<OrderEntity> findByStatus(String status);
    void updateOrderStatus(Long orderId, String status);

    Long getOrderIdByDeliveryPointId(Long deliveryPointId);
}
