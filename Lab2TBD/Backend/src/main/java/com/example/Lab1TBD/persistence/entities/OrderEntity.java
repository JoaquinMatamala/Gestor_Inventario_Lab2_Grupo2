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
    private Long order_id;  // ID UNICO
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp date; // fecha en que se realizo la orden
    private String status;  // estado de la orden (pendiente,pagada,enviada)
    private Float total;    // total a pagar de la orden (OJO, todos los productos del cliente)
    private Long client_id; // identificador foraneo del cliente FK
}
