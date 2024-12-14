package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointRatingEntity {
    private Long point_rating_id;         // Unique ID
    private Long point_id;                // Point ID (FK)
    private Long deliveryman_id;          // Deliveryman ID (FK)
    private Float rating;                  // Rating
    private String comment;               // Comments
}
