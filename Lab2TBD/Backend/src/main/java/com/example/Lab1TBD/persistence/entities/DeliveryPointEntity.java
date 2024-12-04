package com.example.Lab1TBD.persistence.entities;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPointEntity {
    private int deliveryPointId;
    private String deliveryPointName;
    private String latitude;
    private String longitude;
    private String position;
}
