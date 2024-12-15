package com.example.Lab1TBD.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Es el carrito que el cliente usará para pagar multiples productos a la vez.
 */
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    private Long order_id;  // Unique ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp date; // Timestamp for the order
    private String status;  // Order status (pagada, enviada, pendiente, etc)
    private Float total;    // Total price of the order
    private Long delivery_point_id; // Delivery point ID (FK)
    private Long client_id; // Client ID (FK)
}
