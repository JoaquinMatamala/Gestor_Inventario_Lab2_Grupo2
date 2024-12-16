package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryManEntity {
    private Long deliveryman_id;   // Unique ID
    private Long client_id;        // Client ID (FK)
    private Long establishment_id; // Deliveryman establishment (FK)
}
