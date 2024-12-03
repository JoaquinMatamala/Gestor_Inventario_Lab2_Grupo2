package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;
import com.example.Lab1TBD.persistence.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<OrderDetailEntity> getAllOrderDetail(){
        return orderDetailRepository.findAllOrderDetail();
    }

    public OrderDetailEntity getOrderDetailById(Long id) {
        return orderDetailRepository.findOrderDetailById(id);
    }

    public OrderDetailEntity findOrderDetailByPrice(float price) {
        return orderDetailRepository.findOrderDetailByPrice(price);
    }

    public OrderDetailEntity findOrderDetailByQuantity(int quantity) {
        return orderDetailRepository.findOrderDetailByQuantity(quantity);
    }

    public List<OrderDetailEntity> findOrderDetailByOrderId(Long orderId) {
        return orderDetailRepository.findOrderDetailByOrderId(orderId);
    }


    public OrderDetailEntity findOrderDetailByProductId(Long product_id){
        return orderDetailRepository.findOrderDetailByProductId(product_id);
    }

    public void saveOrderDetail(OrderDetailEntity orderDetail) {
        orderDetailRepository.saveOrderDetail(orderDetail);
    }

    public void updateOrderDetail(OrderDetailEntity orderDetail) {
        orderDetailRepository.updateOrderDetail(orderDetail);
    }

    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteOrderDetailById(id);
    }

    public void deleteOrderDetailsByOrderId(Long orderId) {
        orderDetailRepository.deleteOrderDetailsByOrderId(orderId);
    }
}
