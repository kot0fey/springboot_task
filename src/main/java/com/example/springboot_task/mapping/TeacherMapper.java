package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.Teacher;
import com.example.springboot_task.dto.request.TeacherUpdateDto;
import com.example.springboot_task.dto.response.TeacherDto;

public class TeacherMapper {
    public static Teacher mapToTeacher(TeacherUpdateDto teacherUpdateDto, School school) {
        return Teacher.builder()
                .name(school.getName())
                .surname(teacherUpdateDto.getSurname())
                .age(teacherUpdateDto.getAge())
                .learningExperience(teacherUpdateDto.getLearningExperience())
                .subject(teacherUpdateDto.getSubject())
                .school(school)
                .build();
    }

    public static TeacherDto mapToTeacherDto(Teacher teacher) {
        return TeacherDto.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .learningExperience(teacher.getLearningExperience())
                .subject(teacher.getSubject())
                .schoolName(teacher.getSchool().getName())
                .build();
    }
}
