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
    private Long order_detail_id; // Unique ID
    private Long product_id;      // Product ID (FK)
    private int quantity;         // Product quantity
    private Float price;          // Unit price
    private Long order_id;        // Order ID (FK)
}

