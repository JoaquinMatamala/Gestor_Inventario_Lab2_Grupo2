package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    private Long category_id;       // Unique ID
    private String category_name; // Category name
}
