package com.surveyapp.mapping;

import com.surveyapp.dto.SurveyResponseDto;
import com.surveyapp.dto.SurveySubmissionDto;
import com.surveyapp.model.Ratings;
import com.surveyapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RatingsMapper.class})
public interface SurveyMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "user.ratings", target = "ratings")
    SurveyResponseDto toResponseDto(User user);

//    @Mapping(target = "user.id", ignore = true)
//    @Mapping(target = "ratings.id", ignore = true)
//    @Mapping(target = "ratings.user", ignore = true)
//    SurveyResponseDto toResponseDto(SurveySubmissionDto submissionDto);

    // Convert SurveySubmissionDto to User+Ratings (for POST submissions)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    User toUserEntity(SurveySubmissionDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Ratings toRatingsEntity(SurveySubmissionDto dto);

    default SurveySubmissionDto toSubmissionDto(User user, Ratings ratings){
        return new SurveySubmissionDto(
                user.getFullName(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getContactNumber(),
                ratings.getFavoriteFoods(),
                ratings.getMoviesRating(),
                ratings.getRadioRating(),
                ratings.getEatOutRating(),
                ratings.getTvRating()
        );
    }

}
