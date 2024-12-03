package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.OrderEntity;
import com.example.Lab1TBD.services.OrderDetailService;
import com.example.Lab1TBD.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/getall")
    public ResponseEntity<List<OrderEntity>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // consigue una orden a partir de su id asociada
    @GetMapping("/search/id/{id}")
    public ResponseEntity<OrderEntity> getOrdersByIdOrder(@PathVariable Long id){
        OrderEntity foundOrder = orderService.getOrdersByOrderId(id);
        if (foundOrder == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(foundOrder);
    }

    // consigue las ordenes que posean un tipo de estado, independiente del cliente
    @GetMapping("/search/status")
    public ResponseEntity<List<OrderEntity>> getOrdersByStatus(@RequestParam String status){
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    // consigue las ordenes de un cliente a partir del id de ese cliente
    @GetMapping("/search/clientId/{id}")
    public ResponseEntity<List<OrderEntity>> getOrdersByClientId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrdersByClientId(id));
    }

    @PostMapping("/new-order")
    public ResponseEntity<Long> saveOrder(@RequestBody OrderEntity order) {
        Long orderId = orderService.saveOrder(order); // Llama al servicio y obtiene el ID
        return ResponseEntity.ok(orderId); // Retorna el ID generado
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderEntity order){
        orderService.updateOrder(order);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        try {
            orderDetailService.deleteOrderDetailsByOrderId(orderId);
            orderService.deleteOrderById(orderId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update-stock/{orderId}")
    public ResponseEntity<String> updateStock(@PathVariable Long orderId) {
        try {
            orderService.updateStock(orderId);
            return ResponseEntity.ok("Stock actualizado correctamente.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el stock.");
        }
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable("id") Long orderId,
            @RequestBody Map<String, String> requestBody) {
        try {
            // Obtener el nuevo estado desde el cuerpo de la solicitud
            String status = requestBody.get("status");
            if (status == null) {
                return ResponseEntity.badRequest().body("El estado no fue proporcionado.");
            }

            // Actualizar el estado de la orden
            orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok("Estado de la orden actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el estado de la orden: " + e.getMessage());
        }
    }




}
