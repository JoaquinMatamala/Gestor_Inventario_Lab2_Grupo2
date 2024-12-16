package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.LocationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class LocationRepositoryImp implements LocationRepository {

    @Autowired
    private Sql2o sql2o;

    // DEFAULT ------------------------------------------------------------------------------------

    @Override
    public List<LocationEntity> findAllLocations(){
        String query = "SELECT * FROM location";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .executeAndFetch(LocationEntity.class);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LocationEntity findLocationById(Long location_id) {
        String query = "SELECT * FROM location WHERE location_id = :location_id";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .addParameter("location_id", location_id)
                    .executeAndFetchFirst(LocationEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveLocation(LocationEntity location) {
        String query =
                """
                INSERT INTO location
                (latitude, longitude, position, address, location_type)
                VALUES (:latitude, :longitude, ST_GeomFromText(:position, 4326), :address, :location_type)
                """;
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            // Formatear las coordenadas en WKT (Well-Known Text) asegurando el separador decimal correcto
            String positionWKT = String.format(java.util.Locale.US, "POINT(%f %f)", location.getLongitude(), location.getLatitude());

            con.createQuery(query)
                    .addParameter("latitude", location.getLatitude())
                    .addParameter("longitude", location.getLongitude())
                    .addParameter("position", positionWKT) // Pasar WKT al parámetro
                    .addParameter("address", location.getAddress())
                    .addParameter("location_type", location.getLocation_type())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    @Override
    public void updateLocation(LocationEntity location){
        String query =
                """
                UPDATE location
                SET
                address = :address,
                latitude = :latitude,
                longitude = :longitude,
                position = :position,
                location_type = :location_type
                WHERE location_id = :location_id
                """;
        try (org.sql2o.Connection con = sql2o.beginTransaction()){
            con.createQuery(query)
                    .addParameter("address",location.getAddress())
                    .addParameter("latitude",location.getLatitude())
                    .addParameter("longitude",location.getLongitude())
                    .addParameter("position",location.getPosition())
                    .addParameter("location_type",location.getLocation_type())
                    .addParameter("location_id",location.getLocation_id())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLocationById(Long location_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            String sql = "DELETE FROM location WHERE location_id = :location_id";
            con.createQuery(sql)
                    .addParameter("location_id", location_id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }}*/

    // SEARCH -------------------------------------------------------------------------------------

    @Override
    public LocationEntity findLocationWithMaxId() {
        try (org.sql2o.Connection con = sql2o.open()) {
            String query = "SELECT * FROM location ORDER BY location_id DESC LIMIT 1;";
            return con.createQuery(query)
                    .executeAndFetchFirst(LocationEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<LocationEntity> findLocationByType(String location_type) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM location WHERE location_type = :location_type")
                    .addParameter("location_type", location_type)
                    .executeAndFetch(LocationEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<LocationEntity> findLocationByAddress(String address) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM location WHERE address = :address")
                    .addParameter("address", address)
                    .executeAndFetch(LocationEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
