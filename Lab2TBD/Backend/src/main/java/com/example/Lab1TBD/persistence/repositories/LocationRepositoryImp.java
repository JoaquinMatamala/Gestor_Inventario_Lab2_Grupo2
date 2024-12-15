package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.LocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class LocationRepositoryImp implements LocationRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public LocationEntity findLocationById(Long location_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM location WHERE location_id = :location_id")
                    .addParameter("location_id", location_id)
                    .executeAndFetchFirst(LocationEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LocationEntity findLocationByType(String location_type) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM location WHERE location_type = :location_type")
                    .addParameter("location_type", location_type)
                    .executeAndFetchFirst(LocationEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveLocation(LocationEntity location) {
        try (org.sql2o.Connection con = sql2o.open()) {
            String sql = "INSERT INTO location (latitude, longitude, position, address, location_type) " +
                    "VALUES (:latitude, :longitude, ST_GeomFromText(:position, 4326), :address, :location_type)";

            // Formatear las coordenadas en WKT (Well-Known Text) asegurando el separador decimal correcto
            String positionWKT = String.format(java.util.Locale.US, "POINT(%f %f)", location.getLongitude(), location.getLatitude());

            con.createQuery(sql)
                    .addParameter("latitude", location.getLatitude())
                    .addParameter("longitude", location.getLongitude())
                    .addParameter("position", positionWKT) // Pasar WKT al parámetro
                    .addParameter("address", location.getAddress())
                    .addParameter("location_type", location.getLocation_type())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public LocationEntity findLocationWithMaxId() {
        try (org.sql2o.Connection con = sql2o.open()) {
            String sql = "SELECT * FROM location WHERE location_id = (SELECT MAX(location_id) FROM location)";
            return con.createQuery(sql).executeAndFetchFirst(LocationEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la ubicación con el ID más grande");
        }
    }

    @Override
    public void deleteLocation(Long location_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            String sql = "DELETE FROM location WHERE location_id = :location_id";
            con.createQuery(sql)
                    .addParameter("location_id", location_id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
