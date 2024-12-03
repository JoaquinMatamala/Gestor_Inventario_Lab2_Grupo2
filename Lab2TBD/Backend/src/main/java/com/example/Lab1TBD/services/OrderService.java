package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;
import com.example.Lab1TBD.persistence.entities.OrderEntity;
import com.example.Lab1TBD.persistence.repositories.OrderDetailRepository;
import com.example.Lab1TBD.persistence.repositories.OrderRepository;
import com.example.Lab1TBD.persistence.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;



    public OrderEntity getOrdersByOrderId(Long order_id){
        return orderRepository.findByOrderId(order_id);
    }

    public List<OrderEntity> getAllOrders(){
        return orderRepository.findAllOrders();
    }

    public List<OrderEntity> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public List<OrderEntity> getOrdersByStatus(String status){
        return orderRepository.findByStatus(status);
    }

    public Long saveOrder(OrderEntity order) {
        return orderRepository.saveOrder(order); // Retorna el ID generado
    }

    public void updateOrder(OrderEntity order){
        orderRepository.updateOrder(order);
    }

    public void deleteOrderById(Long order_id){
        orderRepository.deleteOrderById(order_id);
    }

    public void updateStock(Long orderId) {
        try {
            // Llama al repositorio para actualizar el stock de todos los productos relacionados con la orden
            productRepository.updateProductStock(orderId);
        } catch (IllegalStateException e) {
            // Maneja el caso donde el stock es insuficiente
            throw new RuntimeException("Error al actualizar el stock: " + e.getMessage(), e);
        } catch (Exception e) {
            // Maneja cualquier otro error inesperado
            throw new RuntimeException("Error inesperado al actualizar el stock: " + e.getMessage(), e);
        }
    }
    public void updateOrderStatus(Long orderId, String status) {
        // Puedes agregar validaciones aquí si es necesario
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        orderRepository.updateOrderStatus(orderId, status);
    }


}