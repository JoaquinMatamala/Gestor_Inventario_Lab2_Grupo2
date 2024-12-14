package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablishmentEntity {
    private Long establishment_id;   // Unique ID
    private String establishment_data;  // Establishment data
    private String region_data;         // Region data
    private Long location_id;           // Location ID (FK)
}
