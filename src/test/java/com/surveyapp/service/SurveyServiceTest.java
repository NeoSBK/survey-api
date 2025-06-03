package com.surveyapp.service;

import com.surveyapp.dto.*;
import com.surveyapp.mapping.RatingsMapper;
import com.surveyapp.mapping.SurveyMapper;
import com.surveyapp.mapping.UserMapper;
import com.surveyapp.repository.RatingsRepository;
import com.surveyapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.surveyapp.model.Ratings;
import com.surveyapp.model.User;

@ExtendWith(MockitoExtension.class)
class SurveyServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RatingsRepository ratingsRepository;

    @Mock
    private SurveyMapper surveyMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RatingsMapper ratingsMapper;

    @Mock
    private SurveyStatisticsCalculator statisticsCalculator;

    @InjectMocks
    private SurveyService surveyService;

    private User user;
    private SurveyResponseDto expectedDto;

    private SurveySubmissionDto submissionDto;
    private User savedUser;
    private SurveyResponseDto expectedResponse;


    @BeforeEach
    void setUp() {
        setupGetAllUserSurveysData();
        setupSubmitSurveyData();
    }

    private void setupGetAllUserSurveysData() {
        user = new User("John Doe", "john@test.com",
                LocalDate.of(1990, 1, 1), "1234567890");
        user.setId(1L);

        Ratings ratings = new Ratings();
        ratings.setId(1L);
        ratings.setFavoriteFoods(Set.of("Pizza"));
        ratings.setMoviesRating(5);
        ratings.setRadioRating(4);
        ratings.setEatOutRating(3);
        ratings.setTvRating(2);
        user.setRatings(ratings);

        UserDto userDto = new UserDto(1L, "John Doe", "john@test.com",
                LocalDate.of(1990, 1, 1), "1234567890", 33);

        RatingsDto ratingsDto = new RatingsDto(1L, Set.of("Pizza"), 5, 4, 3, 2);

        expectedDto = new SurveyResponseDto(userDto, ratingsDto);
    }

    private void setupSubmitSurveyData() {
        submissionDto = new SurveySubmissionDto(
                "John Doe",
                "john@example.com",
                LocalDate.of(1990, 1, 1),
                "1234567890",
                Set.of("Pizza", "Pasta"),
                5, 4, 3, 2
        );
        Ratings unsavedRatings = new Ratings();
        unsavedRatings.setFavoriteFoods(Set.of("Pizza", "Pasta"));
        unsavedRatings.setMoviesRating(5);
        unsavedRatings.setRadioRating(4);
        unsavedRatings.setEatOutRating(3);
        unsavedRatings.setTvRating(2);

        savedUser = new User("John Doe", "john@example.com",
                LocalDate.of(1990, 1, 1), "1234567890");
        savedUser.setId(1L);
        savedUser.setRatings(unsavedRatings);
        unsavedRatings.setUser(savedUser);
        unsavedRatings.setId(1L);

        expectedResponse = new SurveyResponseDto(
                new UserDto(1L, "John Doe", "john@example.com",
                        LocalDate.of(1990, 1, 1), "1234567890", 34),
                new RatingsDto(1L, Set.of("Pizza", "Pasta"), 5, 4, 3, 2)
        );
    }

    @Test
    void getAllUserSurveys_shouldReturnListOfSurveyResponseDto() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(surveyMapper.toResponseDto(user)).thenReturn(expectedDto);

        List<SurveyResponseDto> result = surveyService.getAllUserSurveys();

        assertThat(result)
                .hasSize(1)
                .first()
                .usingRecursiveComparison()
                .isEqualTo(expectedDto);

        verify(userRepository).findAll();
        verify(surveyMapper).toResponseDto(user);
    }

    @Test
    void submitSurvey_shouldSaveUserAndReturnSurveyResponseDto() {
        when(userMapper.toEntity(submissionDto)).thenReturn(savedUser);
        when(ratingsMapper.toEntity(submissionDto)).thenReturn(savedUser.getRatings());
        when(userRepository.save(savedUser)).thenReturn(savedUser);
        when(surveyMapper.toResponseDto(savedUser)).thenReturn(expectedResponse);

        SurveyResponseDto result = surveyService.submitSurvey(submissionDto);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);

        assertThat(savedUser.getRatings().getUser()).isEqualTo(savedUser);
        assertThat(savedUser.getRatings()).isEqualTo(savedUser.getRatings());

        verify(userMapper).toEntity(submissionDto);
        verify(ratingsMapper).toEntity(submissionDto);
        verify(userRepository).save(savedUser);
        verify(surveyMapper).toResponseDto(savedUser);
    }

    @Test
    void getStatistics_shouldReturnSurveyStatisticsDto() {

        long totalSurveys = 10L;

        User user1 = new User("Alice", "alice@test.com", LocalDate.of(1990, 1, 1), "1111");
        User user2 = new User("Bob", "bob@test.com", LocalDate.of(2000, 6, 15), "2222");
        List<User> users = List.of(user1, user2);

        SurveyStatisticsDto expectedStats = new SurveyStatisticsDto(
                totalSurveys,
                31.5,   // avgAge
                33,     // oldest Age
                23,     // youngest Age
                40.0,   // pizza Lovers %
                25.0,   // pasta Lovers %
                10.0,   // papWors Lovers %
                4.5,    // movie Lovers Avg Rating
                3.5,    // radio Lovers Avg Rating
                2.5,    // eatOut Lovers Avg Rating
                5.0     // tv Lovers Avg Rating
        );

        when(ratingsRepository.countTotalSurveys()).thenReturn(totalSurveys);
        when(userRepository.findAllWithDateOfBirth()).thenReturn(users);
        when(statisticsCalculator.Statistics(users, totalSurveys)).thenReturn(expectedStats);

        SurveyStatisticsDto actualStats = surveyService.getStatistics();

        assertThat(actualStats).usingRecursiveComparison().isEqualTo(expectedStats);

        verify(ratingsRepository).countTotalSurveys();
        verify(userRepository).findAllWithDateOfBirth();
    }

}

