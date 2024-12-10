package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.DeliveryPointEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

@Repository
public class DeliveryPointRepositoryImp implements DeliveryPointRepository {
    @Autowired
    Sql2o sql2o;

    @Override
    public DeliveryPointEntity findDeliveryPointById(Long delivery_point_id){
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM delivery_point WHERE delibery_point_id =: delibery_point_id")
                    .addParameter("delibery_point_id",delivery_point_id)
                    .executeAndFetchFirst(DeliveryPointEntity.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DeliveryPointEntity findDeliveryPointByName(String delivery_point_name){
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM delivery_point WHERE delivery_point_name =: delivery_point_name")
                    .addParameter("delivery_point_name",delivery_point_name)
                    .executeAndFetchFirst(DeliveryPointEntity.class);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
