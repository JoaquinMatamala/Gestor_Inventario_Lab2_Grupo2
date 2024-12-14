package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryManEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class DeliveryManRepositoryImp implements DeliveryManRepository {

    @Autowired
    private Sql2o sql2o;

    // Encontrar repartidor por ID
    @Override
    public DeliveryManEntity findDeliveryManById(Long id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM deliveryman WHERE deliveryman_id = :deliveryman_id")
                    .addParameter("deliveryman_id", id)
                    .executeAndFetchFirst(DeliveryManEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Encontrar repartidor por nombre
    @Override
    public DeliveryManEntity findDeliveryManByName(String name) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM deliveryman WHERE deliveryman_name = :deliveryman_name")
                    .addParameter("deliveryman_name", name)
                    .executeAndFetchFirst(DeliveryManEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Guardar repartidor
    @Override
    public void saveDeliveryMan(DeliveryManEntity deliveryMan) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO deliveryman (deliveryman_name, email, phone_number) " +
                            "VALUES (:deliveryman_name, :email, :phone_number)")
                    .addParameter("deliveryman_name", deliveryMan.getDeliveryMan_name())
                    .addParameter("email", deliveryMan.getDeliveryMan_email())
                    .addParameter("phone_number", deliveryMan.getDeliveryMan_phone())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar repartidor por ID
    @Override
    public void updateDeliveryMan(DeliveryManEntity deliveryMan) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("UPDATE deliveryman SET deliveryman_name = :deliveryman_name, email = :email, " +
                            "phone_number = :phone_number WHERE deliveryman_id = :deliveryman_id")
                    .addParameter("deliveryman_id", deliveryMan.getDeliveryMan_id())
                    .addParameter("deliveryman_name", deliveryMan.getDeliveryMan_name())
                    .addParameter("email", deliveryMan.getDeliveryMan_email())
                    .addParameter("phone_number", deliveryMan.getDeliveryMan_phone())
                    .addParameter("establishment_id",deliveryMan.getEstablishment_id())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borrar repartidor por ID
    @Override
    public void deleteDeliveryManById(Long id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("DELETE FROM deliveryman WHERE deliveryman_id = :deliveryman_id")
                    .addParameter("deliveryman_id", id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Registrar login del repartidor
    @Override
    public void logDeliveryManLogin(Long deliveryManId) {
        String sql = """
        INSERT INTO audit_log (user_id, action_type, table_name, executed_query, action_timestamp)
        VALUES (:userId, 'Login', 'audit_log', 'Login', CURRENT_TIMESTAMP)
        """;

        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", deliveryManId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el login en audit_log: " + e.getMessage(), e);
        }
    }

    // Registrar registro del repartidor
    @Override
    public void logDeliveryManRegistration(Long deliveryManId) {
        String sql = """
        INSERT INTO audit_log (user_id, action_type, table_name, executed_query, action_timestamp)
        VALUES (:userId, 'Register', 'audit_log', 'Register', CURRENT_TIMESTAMP)
        """;

        try (org.sql2o.Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", deliveryManId)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el registro en audit_log: " + e.getMessage(), e);
        }
    }
}

