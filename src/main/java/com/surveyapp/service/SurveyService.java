package com.surveyapp.service;

import com.surveyapp.dto.SurveyResponseDto;
import com.surveyapp.dto.SurveyStatisticsDto;
import com.surveyapp.dto.SurveySubmissionDto;
import com.surveyapp.mapping.RatingsMapper;
import com.surveyapp.mapping.SurveyMapper;
import com.surveyapp.mapping.UserMapper;
import com.surveyapp.model.Ratings;
import com.surveyapp.model.User;
import com.surveyapp.repository.RatingsRepository;
import com.surveyapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    private final UserRepository userRepository;
    private final RatingsRepository ratingsRepository;
    private final SurveyMapper surveyMapper;
    private final UserMapper userMapper;
    private final RatingsMapper ratingsMapper;
    private final SurveyStatisticsCalculator statisticsCalculator;

    @Autowired
    public SurveyService(UserRepository userRepository, RatingsRepository ratingsRepository,
                         SurveyMapper surveyMapper, UserMapper userMapper,
                         RatingsMapper ratingsMapper, SurveyStatisticsCalculator statisticsCalculator) {
        this.userRepository = userRepository;
        this.ratingsRepository = ratingsRepository;
        this.surveyMapper = surveyMapper;
        this.userMapper = userMapper;
        this.ratingsMapper = ratingsMapper;
        this.statisticsCalculator = statisticsCalculator;
    }

    @Transactional
    public SurveyResponseDto submitSurvey(SurveySubmissionDto dto) {

        if (userRepository.existsByEmail(dto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A survey has already been submitted with this email.");
        }

        User user = userMapper.toEntity(dto);
        Ratings ratings = ratingsMapper.toEntity(dto);

        ratings.setUser(user);
        user.setRatings(ratings);

        User savedUser = userRepository.save(user);
        return surveyMapper.toResponseDto(savedUser);
    }


    public SurveyStatisticsDto getStatistics() {
        long totalSurveys = ratingsRepository.countTotalSurveys();
        List<User> users = userRepository.findAllWithDateOfBirth();

        return statisticsCalculator.Statistics(users, totalSurveys);
    }

    //For demonstration
    public void deleteAllSurveys() {
        ratingsRepository.deleteAll();
        userRepository.deleteAll();
    }

    //For demonstration
    public List<SurveyResponseDto> getAllUserSurveys() {
        return userRepository.findAll().stream()
                .map(surveyMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
