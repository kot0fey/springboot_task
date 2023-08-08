package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.Teacher;
import com.example.springboot_task.dto.request.TeacherUpdateDto;
import com.example.springboot_task.dto.response.TeacherDto;

public class TeacherMapper {
    public static Teacher mapToTeacher(TeacherUpdateDto teacherUpdateDto, School school) {
        return new Teacher(
                teacherUpdateDto.getId(),
                teacherUpdateDto.getName(),
                teacherUpdateDto.getSurname(),
                teacherUpdateDto.getAge(),
                teacherUpdateDto.getLearningExperience(),
                teacherUpdateDto.getSubject(),
                school
        );
    }

    public static TeacherDto mapToTeacherDto(Teacher teacher) {
        return new TeacherDto(
                teacher.getId(),
                teacher.getName(),
                teacher.getSurname(),
                teacher.getLearningExperience(),
                teacher.getSubject(),
                teacher.getSchool()
        );
    }
}
