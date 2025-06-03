package com.surveyapp.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

public record SurveySubmissionDto(
        @NotNull @Size(max = 100) String fullName,
        @Email String email,
        @Past @NotNull LocalDate dateOfBirth,
        @Pattern(regexp = "^\\d{10}$", message = "Cell number must be exactly 10 digits.")
        String contactNumber,
        @NotEmpty Set<String> favouriteFoods,
        @NotNull @Min(1) @Max(5) Integer moviesRating,
        @NotNull @Min(1) @Max(5) Integer radioRating,
        @NotNull @Min(1) @Max(5) Integer eatOutRating,
        @NotNull @Min(1) @Max(5) Integer tvRating
) {
}
