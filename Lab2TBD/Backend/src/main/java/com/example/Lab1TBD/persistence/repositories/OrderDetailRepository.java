package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;

import java.util.List;

public interface OrderDetailRepository {
    List<OrderDetailEntity> findAllOrderDetail();
    OrderDetailEntity findOrderDetailById(Long order_detail_id);
    OrderDetailEntity findOrderDetailByPrice(float price);
    OrderDetailEntity findOrderDetailByQuantity(int quantity);

    // Buscar detalle de orden por ID de orden
    List<OrderDetailEntity> findOrderDetailByOrderId(Long order_id);

    OrderDetailEntity findOrderDetailByProductId(Long product_id);

    void saveOrderDetail(OrderDetailEntity orderDetail);
    void updateOrderDetail(OrderDetailEntity orderDetail);
    void deleteOrderDetailById(Long order_detail_id);

    void deleteOrderDetailsByOrderId(Long orderId);
}