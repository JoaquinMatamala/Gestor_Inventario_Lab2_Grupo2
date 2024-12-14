package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    private Long product_id;       // Unique ID
    private String product_name;   // Product name
    private String description;    // Product description
    private Float price;           // Product price
    private Integer stock;         // Product stock
    private String product_status; // Product status (available, unavailable)
    private Long category_id;   // Category ID (FK)
}
