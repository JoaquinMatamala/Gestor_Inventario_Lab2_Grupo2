package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.OrderDetailEntity;
import com.example.Lab1TBD.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-detail")
@CrossOrigin("*")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/getall")
    public ResponseEntity<List<OrderDetailEntity>> getAllOrderDetail(){
        return ResponseEntity.ok(orderDetailService.getAllOrderDetail());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrderDetail(@RequestBody OrderDetailEntity orderDetail) {
        try {
            if (orderDetail.getQuantity() <= 0) { // Comprobar solo la cantidad
                return ResponseEntity.badRequest().body("La cantidad debe ser mayor a 0.");
            }

            orderDetailService.saveOrderDetail(orderDetail);
            return ResponseEntity.ok("Detalle de orden creado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al procesar la solicitud.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailEntity> getOrderDetailById(@PathVariable Long id) {
        OrderDetailEntity foundOrderDetail = orderDetailService.getOrderDetailById(id);
        if (foundOrderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(foundOrderDetail);
    }


    @GetMapping("/filter/price/{price}")
    public ResponseEntity<OrderDetailEntity> getOrderDetailByPrice(@PathVariable Float price) {
        OrderDetailEntity orderDetail = orderDetailService.findOrderDetailByPrice(price);
        if (orderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/filter/quantity/{quantity}")
    public ResponseEntity<OrderDetailEntity> getOrderDetailByQuantity(@PathVariable int quantity) {
        OrderDetailEntity orderDetail = orderDetailService.findOrderDetailByQuantity(quantity);
        if (orderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(orderDetail);
    }

    @GetMapping("/filter/order/{orderId}")
    public ResponseEntity<List<OrderDetailEntity>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        List<OrderDetailEntity> orderDetails = orderDetailService.findOrderDetailByOrderId(orderId);
        if (orderDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(orderDetails);
    }


    @GetMapping("/filter/product/{productId}")
    public ResponseEntity<OrderDetailEntity> getOrderDetailByProductId(@PathVariable Long productId) {
        OrderDetailEntity orderDetail = orderDetailService.findOrderDetailByProductId(productId);
        if (orderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(orderDetail);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<OrderDetailEntity> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailEntity updatedOrderDetail) {
        OrderDetailEntity existingOrderDetail = orderDetailService.getOrderDetailById(id);
        if (existingOrderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            updatedOrderDetail.setOrder_detail_id(id);
            orderDetailService.updateOrderDetail(updatedOrderDetail);
            return ResponseEntity.ok(updatedOrderDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        OrderDetailEntity existingOrderDetail = orderDetailService.getOrderDetailById(id);
        if (existingOrderDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}