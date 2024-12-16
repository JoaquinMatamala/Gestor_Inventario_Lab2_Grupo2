package com.example.Lab1TBD.controllers;

import com.example.Lab1TBD.persistence.entities.RatingEntity;
import com.example.Lab1TBD.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@CrossOrigin("*")
public class RatingController {
    @Autowired
    RatingService ratingService;
    
    // --------------------------------------------------------------------------------------------
    @GetMapping("/search/id/{id}")
    public ResponseEntity<RatingEntity> findRatingById(@PathVariable Long id){
        RatingEntity found = ratingService.findRatingById(id);
        return ((id == null) || (found == null)) ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(found);
    }

    @GetMapping("/")
    public ResponseEntity<List<RatingEntity>> findAllRatings(){
        List<RatingEntity> found = ratingService.findAllRatings();
        return found.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(found);
    }
    
    @PostMapping("/")
    public ResponseEntity<Void> saveRating(@RequestBody RatingEntity rating){
        ratingService.saveRating(rating);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateRating(@RequestBody RatingEntity rating){
        ratingService.updateRating(rating);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRatingById(@PathVariable Long id){
        ratingService.deleteRatingById(id);
        return ResponseEntity.noContent().build();
    }
    // --------------------------------------------------------------------------------------------

    @PutMapping("/updateRatingValue")
    public ResponseEntity<Void> updateRatingValue(@RequestParam Long id, @RequestParam Float rating){
        ratingService.updateRatingValue(id,rating);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/score/top/{limit}")
    public ResponseEntity<List<RatingEntity>> findTopRatingsLimitBy(@PathVariable Long limit){
        List<RatingEntity> found = ratingService.findTopRatingsLimitBy(limit);
        return found.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(found);
    }

    @GetMapping("/search/score/higher")
    public ResponseEntity<List<RatingEntity>> findByHigherThanRating(@RequestParam Float rating){
        List<RatingEntity> found = ratingService.findByHigherThanRating(rating);
        return found.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(found);
    }

    @GetMapping("/search/score/lower")
    public ResponseEntity<List<RatingEntity>> findByLowerThanRating(@RequestParam Float rating){
        List<RatingEntity> found = ratingService.findByLowerThanRating(rating);
        return found.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(found);
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<RatingEntity>> findByLocationLike(@RequestParam String location){
        List<RatingEntity> found = ratingService.findByLocationLike(location);
        return found.isEmpty()? ResponseEntity.noContent().build() : ResponseEntity.ok(found);
    }
}
