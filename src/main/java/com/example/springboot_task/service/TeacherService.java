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
        try {
            Long schoolId = teacherUpdateDto.getSchoolId();
            School school = schoolRepository.findById(schoolId).get();
            Teacher teacher = TeacherMapper.mapToTeacher(teacherUpdateDto, school);
            teacherRepository.save(teacher);
        } catch (Exception e) {
            throw new ApiBadRequestException("No school with " + teacherUpdateDto.getSchoolId() + " id found.");
        }
    }

    public TeacherDto getTeacherById(Long id) {
        try {
            Teacher teacher = teacherRepository.findById(id).get();
            TeacherDto teacherDto = TeacherMapper.mapToTeacherDto(teacher);
            return teacherDto;
        } catch (Exception e) {
            throw new ApiBadRequestException("No teacher with " + id + " id found.");
        }
    }

    public ResponseDto getAllTeachers(Integer limit, Integer offset) {
        List fullList = teacherRepository.findAll();
        ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
        return responseDto;
    }

    public void updateTeacher(TeacherUpdateDto teacherUpdateDto) {
        try {
            Long teacherId = teacherUpdateDto.getId();
            Teacher teacher = teacherRepository.findById(teacherId).get();
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
                try {
                    School school = schoolRepository.findById(teacherUpdateDto.getSchoolId()).get();
                    teacher.setSchool(school);
                } catch (Exception e) {
                    throw new ApiBadRequestException("No school with " + teacherUpdateDto.getSchoolId() + " id found.");
                }
            }

        } catch (Exception e) {
            throw new ApiBadRequestException("No teacher with " + teacherUpdateDto.getId() + " id found.");
        }
    }

    public void deleteTeacher(Long id) {
        try {
            teacherRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApiBadRequestException("No teacher with " + id + " id found.");
        }
    }
}
