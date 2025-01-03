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

    // DEFAULT ------------------------------------------------------------------------------------

    @Override
    public List<DeliveryManEntity> findAllDeliveryMen(){
        String query = "SELECT * FROM delivery_man";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .executeAndFetch(DeliveryManEntity.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
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
    public void saveDeliveryMan(Long user_id, Long establishment_id) {
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery("INSERT INTO delivery_man (client_id, establishment_id) VALUES (:client_id, :establishment_id)")
                    .addParameter("client_id", user_id)
                    .addParameter("establishment_id", establishment_id)
                    .executeUpdate();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDeliveryMan(DeliveryManEntity deliveryMan) {
        String query =
                """
                UPDATE delivery_man
                SET
                client_id = :client_id,
                establishment_id = :establishment_id
                WHERE deliveryman_id = :deliveryman_id
                """;
        try (org.sql2o.Connection con = sql2o.beginTransaction()) {
            con.createQuery(query)
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

    // SEARCH -------------------------------------------------------------------------------------

    @Override
    public DeliveryManEntity findDeliveryManByClientId(Long clientId) {
        try (org.sql2o.Connection con = sql2o.open()) {
            System.out.println("Ejecutando consulta para client_id: " + clientId);
            DeliveryManEntity result = con.createQuery("SELECT * FROM delivery_man WHERE client_id = :client_id")
                    .addParameter("client_id", clientId)
                    .executeAndFetchFirst(DeliveryManEntity.class);
            System.out.println("Resultado encontrado: " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DeliveryManEntity> findDeliveryManByEstablishmentId(Long establishmentId){
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM delivery_man WHERE establishment_id = :establishment_d")
                    .addParameter("establishment_id", establishmentId)
                    .executeAndFetch(DeliveryManEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
