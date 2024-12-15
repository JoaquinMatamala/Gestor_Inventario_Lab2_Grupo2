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

    @Override
    public DeliveryPointEntity findDeliveryPointById(Long delivery_point_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM delivery_point WHERE delivery_point_id = :delivery_point_id")
                    .addParameter("delivery_point_id", delivery_point_id)
                    .executeAndFetchFirst(DeliveryPointEntity.class);

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

    // Obtener el promedio de valoración de todos los puntos de entrega
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

    // Actualizar el estado de un punto de entrega (activar/desactivar)
    @Override
    public void updateStatusPoint(Long delivery_point_id, Boolean status) {
        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery("UPDATE delivery_point SET status_point = :status WHERE delivery_point_id = :delivery_point_id")
                    .addParameter("status", status)
                    .addParameter("delivery_point_id", delivery_point_id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long saveDeliveryPoint(DeliveryPointEntity deliveryPoint) {
        try (org.sql2o.Connection con = sql2o.open()) {
            // Ejecutar la consulta y obtener el ID generado
            Long generatedId = con.createQuery(
                            "INSERT INTO delivery_point (delivery_point_name, status_point, rating, comment, delivery_location_point, deliveryman_id, client_id) " +
                                    "VALUES (:delivery_point_name, :status_point, NULL, :comment, :delivery_location_point, NULL, :client_id)", true) // `true` indica que queremos el ID generado
                    .addParameter("delivery_point_name", deliveryPoint.getDelivery_point_name())
                    .addParameter("status_point", deliveryPoint.getStatus_point())
                    .addParameter("comment", deliveryPoint.getComment())
                    .addParameter("delivery_location_point", deliveryPoint.getDelivery_location_point())
                    .addParameter("client_id", deliveryPoint.getClient_id())
                    .executeUpdate()
                    .getKey(Long.class); // Obtener la clave generada como Long

            return generatedId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el punto de entrega", e);
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


}
