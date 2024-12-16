package com.example.Lab1TBD.services;

import com.example.Lab1TBD.persistence.entities.RatingEntity;
import com.example.Lab1TBD.persistence.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    // --------------------------------------------------------------------------------------------
    public RatingEntity findRatingById(Long rating_id){
        return ratingRepository.findRatingById(rating_id);
    }

    public List<RatingEntity> findAllRatings(){
        return ratingRepository.findAllRatings();
    }

    public void saveRating(RatingEntity rating){
        ratingRepository.saveRating(rating);
    }

    public void updateRating(RatingEntity rating){
        ratingRepository.updateRating(rating);
    }

    public void deleteRatingById(Long rating_id){
        ratingRepository.deleteRatingById(rating_id);
    }

    // --------------------------------------------------------------------------------------------
    public void updateRatingValue(Long id, Float rating){
        ratingRepository.updateRatingValue(id, rating);
    }

    public List<RatingEntity> findTopRatingsLimitBy(Long limitBy){
        return ratingRepository.findTopRatingsLimitBy(limitBy);
    }

    public List<RatingEntity> findByHigherThanRating(Float rating){
        return ratingRepository.findByHigherThanRating(rating);
    }

    public List<RatingEntity> findByLowerThanRating(Float rating){
        return ratingRepository.findByLowerThanRating(rating);
    }

    public List<RatingEntity> findByLocationLike(String location){
        return ratingRepository.findByLocationLike(location);
    }
}
