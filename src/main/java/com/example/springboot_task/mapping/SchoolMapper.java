package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.*;
import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import com.example.springboot_task.dto.response.SchoolDTO;
import com.example.springboot_task.service.UserService;

import java.util.List;

public class SchoolMapper {

    public static SchoolDTO mapToSchoolDTO(School school) {
        return new SchoolDTO(
                school.getId(),
                school.getName(),
                school.getUsersNames(),
                school.getTeachersNames(),
                school.getBooksNames(),
                school.getCity().getName()
        );
    }

    public static School mapToSchool(SchoolUpdateDTO schoolDTO, City city) {
        return new School(
                schoolDTO.getId(),
                schoolDTO.getName(),
                city
        );
    }

}
