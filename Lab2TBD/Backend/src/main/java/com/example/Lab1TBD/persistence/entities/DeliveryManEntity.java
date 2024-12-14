package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryManEntity {
    private Long deliveryman_id;         // Unique ID
    private String deliveryman_name;     // Deliveryman name
    private String deliveryman_email;    // Deliveryman email
    private String deliveryman_password; // Deliveryman password
    private String deliveryman_phone;    // Deliveryman phone number
    private Long deliveryman_home_location;  // Deliveryman home location (FK)
    private Long establishment_id;   // Deliveryman establishment (FK)
}
