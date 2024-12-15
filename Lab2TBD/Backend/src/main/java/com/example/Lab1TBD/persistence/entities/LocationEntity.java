package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationEntity {
    private Long location_id;       // Unique ID
    private String address;   // Location name
    private Double latitude;        // Localization latitude
    private Double longitude;       // Localization longitude
    private String position;         // Localization position (Point)
    private Float rating;
    private String location_type;   // Localization type (example: establishment, home, etc.)
}