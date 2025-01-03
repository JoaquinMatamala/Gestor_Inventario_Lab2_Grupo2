package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class DeliveryPointRepositoryImp implements DeliveryPointRepository {
    @Autowired
    Sql2o sql2o;

    // DEFAULT ------------------------------------------------------------------------------------
    @Override
    public List<DeliveryPointEntity> findAllDeliveryPoints(){
        String query = "SELECT * FROM delivery_point";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .executeAndFetch(DeliveryPointEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DeliveryPointEntity findDeliveryPointById(Long delivery_point_id) {
        String query = "SELECT * FROM delivery_point WHERE delivery_point_id = :delivery_point_id";
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery(query)
                    .addParameter("delivery_point_id", delivery_point_id)
                    .executeAndFetchFirst(DeliveryPointEntity.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long saveDeliveryPoint(DeliveryPointEntity deliveryPoint) {
        String query =
                """
                INSERT INTO delivery_point
                (delivery_point_name, status_point, rating, comment, delivery_location_point, deliveryman_id, client_id)
                VALUES (:delivery_point_name, :status_point, :rating, :comment, :delivery_location_point, :deliveryman_id, :client_id)
                """;
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            // Ejecutar la consulta y obtener el ID generado
            Long generatedId = con.createQuery(query, true) // `true` indica que queremos el ID generado
                    .addParameter("delivery_point_name", deliveryPoint.getDelivery_point_name())
                    .addParameter("status_point", deliveryPoint.getStatus_point())
                    .addParameter("rating",deliveryPoint.getRating())
                    .addParameter("comment", deliveryPoint.getComment())
                    .addParameter("delivery_location_point", deliveryPoint.getDelivery_location_point())
                    .addParameter("deliveryman_id", deliveryPoint.getDeliveryman_id())
                    .addParameter("client_id", deliveryPoint.getClient_id())
                    .executeUpdate()
                    .getKey(Long.class); // Obtener la clave generada como Long
            con.commit();
            return generatedId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el punto de entrega", e);
        }
    }

    @Override
    public void updateDeliveryPoint(DeliveryPointEntity deliveryPoint){
        String query =
                """
                UPDATE delivery_point
                SET
                delivery_point_name = :delivery_point_name,
                status_point = :status_point,
                rating = :rating,
                comment = :comment,
                delivery_location_point = :delivery_location_point,
                deliveryman_id = :deliveryman_id,
                client_id = :client_id
                WHERE delivery_point_id = :delivery_point_id
                """;
        try (org.sql2o.Connection con = sql2o.beginTransaction()){
            con.createQuery(query)
                    .addParameter("delivery_point_name",deliveryPoint.getDelivery_point_name())
                    .addParameter("status_point",deliveryPoint.getStatus_point())
                    .addParameter("rating",deliveryPoint.getRating())
                    .addParameter("comment",deliveryPoint.getComment())
                    .addParameter("delivery_location_point",deliveryPoint.getDelivery_location_point())
                    .addParameter("deliveryman_id",deliveryPoint.getDeliveryman_id())
                    .addParameter("client_id",deliveryPoint.getClient_id())
                    .addParameter("delivery_point_id",deliveryPoint.getDelivery_point_id())
                    .executeUpdate();
            con.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDeliveryPointById(Long delivery_point_id){
        String query = "DELETE FROM delivery_point WHERE delivery_point_id = :delivery_point_id";
        try (org.sql2o.Connection con =sql2o.beginTransaction()){
            con.createQuery(query)
                    .addParameter("delivery_point_id",delivery_point_id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // SEARCH -------------------------------------------------------------------------------------

    @Override
    public List<DeliveryPointEntity> findAllDeliveryPointsByIdClient(Long client_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM delivery_point WHERE client_id = :client_id")
                    .addParameter("client_id", client_id)
                    .executeAndFetch(DeliveryPointEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Float findAllDeliveryPointsAVG() {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT AVG(rating) FROM delivery_point")
                    .executeScalar(Float.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DeliveryPointEntity findDeliveryPointByName(String delivery_point_name) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM delivery_point WHERE delivery_point_name = :delivery_point_name")
                    .addParameter("delivery_point_name", delivery_point_name)
                    .executeAndFetchFirst(DeliveryPointEntity.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public DeliveryPointEntity findDeliveryPointForClientAndLocation(Long clientId, Long locationId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            String sql = "SELECT * FROM delivery_point " +
                    "WHERE client_id = :clientId AND delivery_location_point = :locationId " +
                    "AND deliveryman_id IS NULL";

            return con.createQuery(sql)
                    .addParameter("clientId", clientId)
                    .addParameter("locationId", locationId)
                    .executeAndFetchFirst(DeliveryPointEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long findLocationIdByDeliveryPointId(Long deliveryPointId){
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT delivery_location_point FROM delivery_point WHERE delivery_point_id = :delivery_point_id")
                    .addParameter("delivery_point_id", deliveryPointId)
                    .executeScalar(Long.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    @Override
    public List<DeliveryPointEntity> findDeliveryPointByDeliveryManId(Long deliveryManId){
        try(org.sql2o.Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM delivery_point WHERE deliveryman_id = :deliveryManId")
                    .addParameter("deliveryManId", deliveryManId)
                    .executeAndFetch(DeliveryPointEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateStatusPoint(Long delivery_point_id, Boolean status) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("UPDATE delivery_point SET status_point = :status WHERE delivery_point_id = :delivery_point_id")
                    .addParameter("status", status)
                    .addParameter("delivery_point_id", delivery_point_id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDeliveryManId(Long deliveryPointId, Long deliveryManId){
        try(org.sql2o.Connection con = sql2o.beginTransaction()){
            con.createQuery("UPDATE delivery_point SET deliveryman_id = :deliveryManId WHERE delivery_point_id = :deliveryPointId")
                    .addParameter("deliveryManId", deliveryManId)
                    .addParameter("deliveryPointId", deliveryPointId)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    @Override
    public Boolean verifyDeliveryManInArea(){
        try(org.sql2o.Connection con = sql2o.open()){
            con.createQuery("SELECT
    dp.delivery_point_id,
    dp.delivery_point_name,
    dp.comment,
    l.position,
    ag.nombre AS area_nombre,
    ST_Within(l.position, ag.area) AS dentro_del_area
FROM
    delivery_point dp
        JOIN
    location l ON dp.delivery_location_point = l.location_id
        JOIN
    areas_geograficas ag ON ST_Within(l.position, ag.area)
WHERE
    dp.status_point = TRUE;")
        }
    }*/

    @Override
    public void updateRating(Long delivery_point_id, Float rating){
        try(org.sql2o.Connection con = sql2o.beginTransaction()){
            con.createQuery("UPDATE delivery_point SET rating = :rating WHERE delivery_point_id = :delivery_point_id")
                    .addParameter("rating", rating)
                    .addParameter("delivery_point_id", delivery_point_id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
