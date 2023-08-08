package com.example.springboot_task.service;

import com.example.springboot_task.domain.*;
import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import com.example.springboot_task.dto.response.ResponseDto;
import com.example.springboot_task.dto.response.SchoolDTO;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.SchoolMapper;
import com.example.springboot_task.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final BookRepository bookRepository;

    public SchoolDTO createSchool(SchoolUpdateDTO schoolUpdateDTO) {
            Long cityId = schoolUpdateDTO.getCityId();
            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new ApiBadRequestException("No city with " + schoolUpdateDTO.getCityId() + " id found."));
            List<Long> usersId = schoolUpdateDTO.getUsersId();
            List<User> users = userRepository.findAllById(usersId);
            List<Long> teachersId = schoolUpdateDTO.getTeachersId();
            List<Teacher> teachers = teacherRepository.findAllById(teachersId);
            List<Long> booksId = schoolUpdateDTO.getBooksId();
            List<Book> books = bookRepository.findAllById(booksId);
            School school = schoolRepository.save(SchoolMapper.mapToSchool(schoolUpdateDTO, users, teachers, books, city));
            return SchoolMapper.mapToSchoolDTO(school);
    }

    public ResponseDto<SchoolDTO> getAllSchools(Integer limit, Integer offset) {
        List fullList = schoolRepository
                .findAll()
                .stream()
                .map(SchoolMapper::mapToSchoolDTO)
                .collect(Collectors.toList());
        ResponseDto responseDto = new ResponseDto(fullList, limit, offset);
        return responseDto;
    }

    public SchoolDTO getSchoolById(long id) throws ApiBadRequestException {
            School school = schoolRepository.findById(id)
                    .orElseThrow(() -> new ApiBadRequestException("No schools with " + id + " id found"));
            return SchoolMapper.mapToSchoolDTO(school);
    }

    public SchoolDTO deleteSchoolById(Long id) throws ApiBadRequestException {
            School school = schoolRepository.findById(id)
                    .orElseThrow(() -> new ApiBadRequestException("No schools with " + id + " id found"));
            schoolRepository.deleteById(id);
            return SchoolMapper.mapToSchoolDTO(school);
    }

    @Transactional
    public SchoolDTO updateSchool(SchoolUpdateDTO schoolUpdateDTO) throws ApiBadRequestException {
            Long schoolId = schoolUpdateDTO.getId();
            School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new ApiBadRequestException("No schools with " + schoolUpdateDTO.getId() + " id found"));

            if (schoolUpdateDTO.getName() != null) {
                school.setName(schoolUpdateDTO.getName());
            }
            if (schoolUpdateDTO.getUsersId() != null) {
                    List<Long> usersId = schoolUpdateDTO.getUsersId();
                    List<User> users = userRepository.findAllById(usersId);
                    school.setUsers(users);
            }
            if (schoolUpdateDTO.getTeachersId()!=null){
                    List<Long> teachersId = schoolUpdateDTO.getTeachersId();
                    List<Teacher> teachers = teacherRepository.findAllById(teachersId);
                    school.setTeachers(teachers);
            }
            if (schoolUpdateDTO.getBooksId()!=null){
                    List<Long> booksId = schoolUpdateDTO.getBooksId();
                    List<Book> books = bookRepository.findAllById(booksId);
                    school.setBooks(books);
            }
            if (schoolUpdateDTO.getCityId() != null) {
                    City city = cityRepository.findById(schoolUpdateDTO.getCityId())
                            .orElseThrow(() -> new ApiBadRequestException("No city with " + schoolUpdateDTO.getCityId() + " id found."));
                    school.setCity(city);
            }

            school = schoolRepository.save(school);
            return SchoolMapper.mapToSchoolDTO(school);

    }

}
