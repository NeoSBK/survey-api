package com.surveyapp.dto;

import java.time.LocalDate;

public record UserDto(
        Long id,
        String fullName,
        String email,
        LocalDate dateOfBirth,
        String contactNumber,
        int age
) {
}
