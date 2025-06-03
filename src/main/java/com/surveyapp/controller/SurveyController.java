package com.surveyapp.controller;

import com.surveyapp.dto.SurveyResponseDto;
import com.surveyapp.dto.SurveyStatisticsDto;
import com.surveyapp.dto.SurveySubmissionDto;
import com.surveyapp.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping
    public ResponseEntity<SurveyResponseDto> createSurvey(@Valid @RequestBody SurveySubmissionDto dto) {
        SurveyResponseDto response = surveyService.submitSurvey(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/statistics")
    public ResponseEntity<SurveyStatisticsDto> statistics() {
        SurveyStatisticsDto stats = surveyService.getStatistics();
        return ResponseEntity.ok(stats);
    }

    //For Demonstration
    @GetMapping("/all")
    public ResponseEntity<List<SurveyResponseDto>> getAllUserSurveys() {
        List<SurveyResponseDto> responses = surveyService.getAllUserSurveys();
        return ResponseEntity.ok(responses);
    }

    //For Demonstration
    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllSurveys() {
        surveyService.deleteAllSurveys();
        return ResponseEntity.ok("All surveys deleted successfully.");
    }
}
