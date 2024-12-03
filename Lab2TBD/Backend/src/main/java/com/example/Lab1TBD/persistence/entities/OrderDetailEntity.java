package com.example.Lab1TBD.persistence.entities;

import lombok.*;

/**
 * Si "OrderEntity" es el carrito, este seria un producto en particular del carrito.
 * No confundir con el Producto como tal.
 */
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {
    private Long order_detail_id; // ID UNICO
    private int quantity;         // cantidad del producto en la orden
    private Float price;          // precio unitario del producto
    private Long order_id;        // id foraneo que pertenece a la orden FK
    private Long product_id;      // id foraneo que pertenece al producto FK
}

