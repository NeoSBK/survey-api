package com.surveyapp.mapping;

import com.surveyapp.dto.RatingsDto;
import com.surveyapp.dto.SurveySubmissionDto;
import com.surveyapp.model.Ratings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingsMapper {

    RatingsDto toDto(Ratings ratings);

    @Mapping(source = "favouriteFoods", target = "favoriteFoods")
    @Mapping(source = "moviesRating", target = "moviesRating")
    @Mapping(source = "radioRating", target = "radioRating")
    @Mapping(source = "eatOutRating", target = "eatOutRating")
    @Mapping(source = "tvRating", target = "tvRating")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Ratings toEntity(SurveySubmissionDto dto);
}


