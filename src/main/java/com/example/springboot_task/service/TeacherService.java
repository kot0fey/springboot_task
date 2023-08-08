package com.example.springboot_task.service;

import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.Teacher;
import com.example.springboot_task.dto.request.TeacherUpdateDto;
import com.example.springboot_task.dto.response.ResponseDto;
import com.example.springboot_task.dto.response.TeacherDto;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.TeacherMapper;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;

    public void createTeacher(TeacherUpdateDto teacherUpdateDto) {
            Long schoolId = teacherUpdateDto.getSchoolId();
            School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new ApiBadRequestException("No school with " + teacherUpdateDto.getSchoolId() + " id found."));
            Teacher teacher = TeacherMapper.mapToTeacher(teacherUpdateDto, school);
            teacherRepository.save(teacher);
    }

    public TeacherDto getTeacherById(Long id) {
            Teacher teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new ApiBadRequestException("No teacher with " + id + " id found."));
            TeacherDto teacherDto = TeacherMapper.mapToTeacherDto(teacher);
            return teacherDto;
    }

    public ResponseDto getAllTeachers(Integer limit, Integer offset) {
        List fullList = teacherRepository.findAll();
        ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
        return responseDto;
    }

    public void updateTeacher(TeacherUpdateDto teacherUpdateDto) {
            Long teacherId = teacherUpdateDto.getId();
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new ApiBadRequestException("No teacher with " + teacherUpdateDto.getId() + " id found."));
            if (teacherUpdateDto.getName() != null) {
                teacher.setName(teacherUpdateDto.getName());
            }
            if (teacherUpdateDto.getSurname() != null) {
                teacher.setSurname(teacherUpdateDto.getSurname());
            }
            if (teacherUpdateDto.getAge() != null) {
                teacher.setAge(teacherUpdateDto.getAge());
            }
            if (teacherUpdateDto.getLearningExperience() != null) {
                teacher.setLearningExperience(teacherUpdateDto.getLearningExperience());
            }
            if (teacherUpdateDto.getSubject() != null) {
                teacher.setSubject(teacherUpdateDto.getSubject());
            }
            if (teacherUpdateDto.getSchoolId() != null) {
                    School school = schoolRepository.findById(teacherUpdateDto.getSchoolId())
                            .orElseThrow(() -> new ApiBadRequestException("No school with " + teacherUpdateDto.getSchoolId() + " id found."));
                    teacher.setSchool(school);
            }


    }

    public void deleteTeacher(Long id) {
        teacherRepository.findById(id)
                        .orElseThrow(() -> new ApiBadRequestException("No teacher with " + id + " id found."));
            teacherRepository.deleteById(id);
    }
}
