package com.surveyapp.repository;

import com.surveyapp.model.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingsRepository extends JpaRepository<Ratings, Long> {

    @Query("SELECT COUNT(r) FROM Ratings r")
    Long countTotalSurveys();

    @Query("SELECT COUNT(DISTINCT r.id) FROM Ratings r JOIN r.favoriteFoods f WHERE LOWER(f) = 'pizza'")
    long countPizzaLovers();

    @Query("SELECT COUNT(DISTINCT r.id) FROM Ratings r JOIN r.favoriteFoods f WHERE LOWER(f) = 'pasta'")
    long countPastaLovers();

    @Query("SELECT COUNT(DISTINCT r.id) FROM Ratings r JOIN r.favoriteFoods f WHERE LOWER(f) = 'pap and wors'")
    long countPapAndWorsLovers();

    @Query("SELECT AVG(r.moviesRating) FROM Ratings r")
    Double findAverageMovieRating();

    @Query("SELECT AVG(r.radioRating) FROM Ratings r")
    Double findAverageRadioRating();

    @Query("SELECT AVG(r.eatOutRating) FROM Ratings r")
    Double findAverageEatOutRating();

    @Query("SELECT AVG(r.tvRating) FROM Ratings r")
    Double findAverageTvRating();
}
