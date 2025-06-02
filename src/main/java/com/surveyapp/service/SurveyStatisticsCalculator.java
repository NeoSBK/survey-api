package com.surveyapp.service;

import com.surveyapp.dto.SurveyStatisticsDto;
import com.surveyapp.model.User;
import com.surveyapp.repository.RatingsRepository;
import com.surveyapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Supplier;

@Component
public class SurveyStatisticsCalculator {

    private final UserRepository userRepository;

    private final RatingsRepository ratingsRepository;

    @Autowired
    public SurveyStatisticsCalculator(UserRepository userRepository, RatingsRepository ratingsRepository) {
        this.userRepository = userRepository;
        this.ratingsRepository = ratingsRepository;
    }

    public SurveyStatisticsDto Statistics(List<User> users, long totalSurveys){
        double averageAge = averageAge(users);
        int oldestAge = calculateAge(userRepository.findOldestDob());
        int youngestAge = calculateAge(userRepository.findYoungestDob());

        double pizzaLoversPercentage = calculatePercentage(totalSurveys, ratingsRepository::countPizzaLovers);
        double pastaLoversPercentage = calculatePercentage(totalSurveys,ratingsRepository::countPastaLovers);
        double papWorsLoversPercentage = calculatePercentage(totalSurveys,ratingsRepository::countPapAndWorsLovers);

        double movieLoversAverageRating = calculateAverage(ratingsRepository::findAverageMovieRating);
        double radioLoversAverageRating = calculateAverage(ratingsRepository::findAverageRadioRating);
        double eatOutLoversAverageRating = calculateAverage(ratingsRepository::findAverageEatOutRating);
        double tvLoversAverageRating = calculateAverage(ratingsRepository::findAverageTvRating);

        return new SurveyStatisticsDto(
                totalSurveys,
                averageAge,
                oldestAge,
                youngestAge,
                pizzaLoversPercentage,
                pastaLoversPercentage,
                papWorsLoversPercentage,
                movieLoversAverageRating,
                radioLoversAverageRating,
                eatOutLoversAverageRating,
                tvLoversAverageRating
        );
    }

    private double averageAge(List<User> users){
        double avgAge = users.stream()
                .mapToInt(user -> Period.between(user.getDateOfBirth(), LocalDate.now()).getYears())
                .average()
                .orElse(0.0);
        return roundToOneDecimalPlace(avgAge);
    }

    private int calculateAge(LocalDate dob){
        return dob != null ? Period.between(dob, LocalDate.now()).getYears() : 0;
    }

    private double calculatePercentage(long totalSurveys, Supplier<Long> countSupplier) {
        if (totalSurveys == 0) return 0.0;
        long count = countSupplier.get();
        return roundToOneDecimalPlace((double) count * 100 / totalSurveys);
    }

    private double calculateAverage(Supplier<Double> averageSupplier) {
        Double avg = averageSupplier.get();
        return avg == null ? 0.0 : roundToOneDecimalPlace(avg);
    }

    private double roundToOneDecimalPlace(double value){
        return BigDecimal
                .valueOf(value)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
