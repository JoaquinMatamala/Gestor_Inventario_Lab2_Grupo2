package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.EstablishmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class EstablishmentRepositoryImp implements EstablishmentRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public EstablishmentEntity findEstablishmentById(Long establishment_id){
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM view_establishment WHERE establishment_id = :establishment_id")
                    .addParameter("establishment_id",establishment_id)
                    .executeAndFetchFirst(EstablishmentEntity.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<EstablishmentEntity> findAllEstablishments(){
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM view_establishment")
                    .executeAndFetch(EstablishmentEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EstablishmentEntity findEstablishmentByRegion(String region_data){
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM view_estavlishment WHERE region_data =:region_data")
                    .addParameter("region_data",region_data)
                    .executeAndFetchFirst(EstablishmentEntity.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
