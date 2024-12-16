package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryManEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import java.util.List;

@Repository
public class DeliveryManRepositoryImp implements DeliveryManRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public DeliveryManEntity findDeliveryManById(Long id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM delivery_man WHERE deliveryman_id = :deliveryman_id")
                    .addParameter("deliveryman_id", id)
                    .executeAndFetchFirst(DeliveryManEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryManEntity> findDeliveryManByClientId(String clientId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM delivery_man WHERE client_id = :client_id")
                    .addParameter("client_id", clientId)
                    .executeAndFetch(DeliveryManEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveDeliveryMan(DeliveryManEntity deliveryMan) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO delivery_man (client_id, establishment_id) VALUES (:client_id, :establishment_id)")
                    .addParameter("client_id", deliveryMan.getClient_id())
                    .addParameter("establishment_id", deliveryMan.getEstablishment_id())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDeliveryMan(DeliveryManEntity deliveryMan) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("UPDATE delivery_man SET client_id = :client_id, establishment_id = :establishment_id WHERE deliveryman_id = :deliveryman_id")
                    .addParameter("deliveryman_id", deliveryMan.getDeliveryman_id())
                    .addParameter("client_id", deliveryMan.getClient_id())
                    .addParameter("establishment_id", deliveryMan.getEstablishment_id())
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDeliveryManById(Long id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("DELETE FROM delivery_man WHERE deliveryman_id = :deliveryman_id")
                    .addParameter("deliveryman_id", id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
