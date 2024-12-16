package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.RatingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class RatingRepositoryImp implements RatingRepository{
    @Autowired
    private Sql2o sql2o;

    // DEFAULT ------------------------------------------------------------------------------------
    @Override
    public RatingEntity findRatingById(Long rating_id) {
        try (org.sql2o.Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM rating WHERE rating_id = :rating_id")
                    .addParameter("rating_id", rating_id)
                    .executeAndFetchFirst(RatingEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RatingEntity> findAllRatings() {
        String query = "SELECT * FROM rating";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .executeAndFetch(RatingEntity.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveRating(RatingEntity ratingEntity){
        String query = "INSERT INTO rating (address, rating) "+
                       "VALUES (:address, :rating)";
        try (org.sql2o.Connection con = sql2o.beginTransaction()){
            con.createQuery(query)
                    .addParameter("address",ratingEntity.getAddress())
                    .addParameter("rating",ratingEntity.getRating())
                    .executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateRating(RatingEntity ratingEntity){
        String query = "UPDATE rating SET address = :address, rating = :rating WHERE rating_id = :rating_id";
        try (org.sql2o.Connection con = sql2o.beginTransaction()){
            con.createQuery(query)
                    .addParameter("address",ratingEntity.getAddress())
                    .addParameter("rating",ratingEntity.getRating())
                    .addParameter("rating_id",ratingEntity.getRating_id())
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRatingById(Long rating_id){
        String query = "DELETE FROM rating WHERE rating_id = :rating_id";
        try (org.sql2o.Connection con = sql2o.beginTransaction()){
            con.createQuery(query)
                    .addParameter("rating_id",rating_id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------------------------------
    @Override
    public void updateRatingValue(Long rating_id, Float rating){
        String query = "UPDATE rating SET rating = :rating WHERE rating_id = :rating_id";
        try (org.sql2o.Connection con = sql2o.beginTransaction()){
            con.createQuery(query)
                    .addParameter("rating",rating)
                    .addParameter("rating_id",rating_id)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RatingEntity> findByHigherThanRating(Float rating){
        String query = "SELECT * FROM rating WHERE rating > :rating";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .addParameter("rating",rating)
                    .executeAndFetch(RatingEntity.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RatingEntity> findByLowerThanRating(Float rating){
        String query = "SELECT * FROM rating WHERE rating < :rating";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .addParameter("rating",rating)
                    .executeAndFetch(RatingEntity.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RatingEntity> findTopRatingsLimitBy(Long limitBy){
        String query = "SELECT * FROM rating ORDER BY rating DESC LIMIT :limitBy";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .addParameter("limitBy",limitBy)
                    .executeAndFetch(RatingEntity.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RatingEntity> findByLocationLike(String location){
        String query = "SELECT * FROM rating WHERE address LIKE :location";
        try (org.sql2o.Connection con = sql2o.open()){
            return con.createQuery(query)
                    .addParameter("location",location)
                    .executeAndFetch(RatingEntity.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
