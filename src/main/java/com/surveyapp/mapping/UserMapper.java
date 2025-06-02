package com.surveyapp.mapping;

import com.surveyapp.dto.SurveySubmissionDto;
import com.surveyapp.dto.UserDto;
import com.surveyapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    User toEntity(SurveySubmissionDto dto);

    @Mapping(target = "age", expression = "java(calculateAge(user.getDateOfBirth()))")
    UserDto toDto(User user);

    default int calculateAge(LocalDate dob) {
        return dob != null ? Period.between(dob, LocalDate.now()).getYears() : 0;
    }
}


