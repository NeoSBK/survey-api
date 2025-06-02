package com.surveyapp.dto;

public record SurveyStatisticsDto(
        long totalSurveys,
        double averageAge,
        int oldestAge,
        int youngestAge,
        double pizzaLoversPercentage,
        double pastaLoversPercentage,
        double papWorsLoversPercentage,
        double movieLoversAverageRating,
        double radioLoversAverageRating,
        double eatOutLoversAverageRating,
        double tvLoversAverageRating
) {
}
