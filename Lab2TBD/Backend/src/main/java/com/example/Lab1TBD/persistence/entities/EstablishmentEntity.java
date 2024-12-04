package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablishmentEntity {
    private Integer establishment_id;
    private String establishment_data;
    private String region_data;
    private String latitude;
    private String longitude;
    private String position;
}
