package com.surveyapp.dto;

import java.util.Set;

public record RatingsDto(
        Long id,
        Set<String> favoriteFoods,
        int moviesRating,
        int radioRating,
        int eatOutRating,
        int tvRating
) {
}
