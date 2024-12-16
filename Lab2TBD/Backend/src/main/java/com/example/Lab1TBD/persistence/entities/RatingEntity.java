package com.example.Lab1TBD.persistence.entities;

import lombok.*;
// Como el repartidor califica el lugar.
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingEntity {
    private Long rating_id;   // Unique ID
    private String address;   // Location name
    private Float rating;     // Rating
}
