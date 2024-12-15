package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.LocationEntity;

public interface LocationRepository {

    LocationEntity findLocationById(Long location_id);

    LocationEntity findLocationByType(String location_type);



    void saveLocation(LocationEntity location);

    LocationEntity findLocationWithMaxId();

    void deleteLocation(Long location_id);
}
