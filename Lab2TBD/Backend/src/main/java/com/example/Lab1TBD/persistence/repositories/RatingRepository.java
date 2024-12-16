package com.example.Lab1TBD.persistence.repositories;

import com.example.Lab1TBD.persistence.entities.RatingEntity;

import java.util.List;

public interface RatingRepository {
    //DEFAULT
    RatingEntity findRatingById(Long rating_id);
    List<RatingEntity> findAllRatings();
    void saveRating(RatingEntity rating);
    void updateRating(RatingEntity rating);
    void deleteRatingById(Long rating_id);

    //
    void updateRatingValue(Long id, Float rating);
    List<RatingEntity> findTopRatingsLimitBy(Long limitBy);
    List<RatingEntity> findByHigherThanRating(Float rating);
    List<RatingEntity> findByLowerThanRating(Float rating);
    List<RatingEntity> findByLocationLike(String location);
}
