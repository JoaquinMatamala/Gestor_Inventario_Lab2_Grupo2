package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPointEntity {
    private Long delivery_point_id;        // Unique ID
    private String delivery_point_name;   // Delivery point name (example: casa, oficina,  etc)
    private Boolean status_point;
    private Float rating;   // Point selected by the client  (0: no ; 1: yes)
    private Long delivery_location_point;  // Delivery location (FK)
    private Long client_id;          // Client ID (FK)
}
