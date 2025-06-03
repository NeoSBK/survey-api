package com.surveyapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surveyapp.dto.RatingsDto;
import com.surveyapp.dto.SurveyResponseDto;
import com.surveyapp.dto.SurveySubmissionDto;
import com.surveyapp.dto.UserDto;
import com.surveyapp.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.Set;

@WebMvcTest(SurveyController.class)
public class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SurveyService surveyService;

    private static final String BASE_URL = "/v1/api/survey";

    // Test data builders
    private SurveySubmissionDto createValidSubmission() {
        return new SurveySubmissionDto(
                "John Doe",
                "john.doe@example.com",
                LocalDate.of(1990, 5, 15),
                "0234567890",
                Set.of("Pizza", "Pasta"),
                4, 3, 5, 2
        );
    }

    private SurveyResponseDto createSampleResponse() {
        return new SurveyResponseDto(
                new UserDto(1L, "John Doe", "john.doe@example.com",
                        LocalDate.of(1990, 5, 15), "0234567890", 33),
                new RatingsDto(1L, Set.of("Pizza", "Pasta"), 4, 3, 5, 2)
        );
    }

    //Happy Path
    @Test
    void submitSurvey_WithValidData_ReturnsCreated() throws Exception {
        SurveySubmissionDto submission = createValidSubmission();
        SurveyResponseDto response = createSampleResponse();

        when(surveyService.submitSurvey(any(SurveySubmissionDto.class)))
                .thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(submission)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.fullName").value("John Doe"))
                .andExpect(jsonPath("$.user.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.ratings.id").value(1))
                .andExpect(jsonPath("$.ratings.moviesRating").value(4))
                .andExpect(jsonPath("$.ratings.favoriteFoods.length()").value(2));
    }

    //Edge Cases
    @Test
    void submitSurvey_WithMinimalData_ReturnsCreated() throws Exception {
        SurveySubmissionDto minimalSubmission = new SurveySubmissionDto(
                "Jane Smith",
                "jane@example.com",
                LocalDate.of(1985, 10, 20),
                null,
                Set.of("Burger"),
                1, 1, 1, 1
        );

        SurveyResponseDto response = new SurveyResponseDto(
                new UserDto(2L, "Jane Smith", "jane@example.com",
                        LocalDate.of(1985, 10, 20), null, 38),
                new RatingsDto(2L, Set.of("Burger"), 1, 1, 1, 1)
        );

        when(surveyService.submitSurvey(any(SurveySubmissionDto.class)))
                .thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(minimalSubmission)))
                .andExpect(status().isCreated());
    }
}