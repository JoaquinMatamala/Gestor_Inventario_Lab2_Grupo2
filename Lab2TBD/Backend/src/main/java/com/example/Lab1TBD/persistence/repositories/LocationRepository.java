package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.LocationEntity;

import java.util.List;

public interface LocationRepository {
    //DEFAULT
    List<LocationEntity> findAllLocations();
    LocationEntity findLocationById(Long location_id);
    void saveLocation(LocationEntity location);
    void deleteLocationById(Long location_id);

    //SEARCH
    LocationEntity findLocationWithMaxId();
    List<LocationEntity> findLocationByType(String location_type);
    List<LocationEntity> findLocationByAddress(String address);
}
