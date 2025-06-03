package com.surveyapp.repository;

import com.surveyapp.model.Ratings;
import com.surveyapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static java.time.LocalDate.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RatingsRepositoryTest {

    @Autowired
    private RatingsRepository ratingsRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        ratingsRepository.deleteAll();
        userRepository.deleteAll();

        User user1 = userRepository.save(new User("User One", "user1@test.com",
                of(1990, 1, 1), "1111111111"));
        User user2 = userRepository.save(new User("User Two", "user2@test.com",
                of(1985, 5, 5), "2222222222"));
        User user3 = userRepository.save(new User("User Three", "user3@test.com",
                of(1995, 10, 10), "3333333333"));

        Ratings ratings1 = createRatings(user1, Set.of("Pizza", "Pasta", "Pap and Wors"), 5, 3, 4, 2);
        Ratings ratings2 = createRatings(user2, Set.of("Pizza", "Pap and Wors"), 1, 5, 3, 4);
        Ratings ratings3 = createRatings(user3, Set.of("Pasta"), 4, 1, 3, 5);

    }

    private Ratings createRatings(User user, Set<String> foods, Integer movies, Integer radio, Integer eatOut, Integer tv) {
        Ratings ratings = new Ratings();
        ratings.setUser(user);
        ratings.setFavoriteFoods(foods);
        if (movies != null) ratings.setMoviesRating(movies);
        if (radio != null) ratings.setRadioRating(radio);
        if (eatOut != null) ratings.setEatOutRating(eatOut);
        if (tv != null) ratings.setTvRating(tv);
        return ratingsRepository.save(ratings);
    }

    @Test
    void shouldReturnCorrectSurveyCounts() {
        assertThat(ratingsRepository.countTotalSurveys()).isEqualTo(3);
    }

    @Test
    void shouldCountFoodPreferencesCorrectly() {
        assertThat(ratingsRepository.countPizzaLovers()).isEqualTo(2);
        assertThat(ratingsRepository.countPastaLovers()).isEqualTo(2);
        assertThat(ratingsRepository.countPapAndWorsLovers()).isEqualTo(2);
    }

    @Test
    void shouldCalculateAverageRatingsCorrectly() {
        assertThat(ratingsRepository.findAverageMovieRating()).isEqualTo(3.3333333333333335);
        assertThat(ratingsRepository.findAverageRadioRating()).isEqualTo(3.0);
        assertThat(ratingsRepository.findAverageEatOutRating()).isEqualTo(3.3333333333333335);
        assertThat(ratingsRepository.findAverageTvRating()).isEqualTo(3.6666666666666665);
    }
}
