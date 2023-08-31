package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.*;
import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import com.example.springboot_task.dto.response.SchoolDTO;
import com.example.springboot_task.service.UserService;

import java.util.List;

public class SchoolMapper {

    public static SchoolDTO mapToSchoolDTO(School school) {
        return SchoolDTO.builder()
                .id(school.getId())
                .name(school.getName())
                .usersNames(school.getUsersNames())
                .teachersNames(school.getTeachersNames())
                .booksNames(school.getBooksNames())
                .cityName(school.getCity().getName())
                .build();
    }

    public static School mapToSchool(SchoolUpdateDTO schoolDTO, City city) {
        return School.builder()
                .name(schoolDTO.getName())
                .city(city)
                .build();
    }

}
