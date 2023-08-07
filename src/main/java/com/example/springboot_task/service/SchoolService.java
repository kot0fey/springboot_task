package com.example.springboot_task.service;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import com.example.springboot_task.dto.response.SchoolDTO;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.SchoolMapper;
import com.example.springboot_task.repository.CityRepository;
import com.example.springboot_task.repository.SchoolRepository;
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

    public SchoolDTO createSchool(SchoolUpdateDTO schoolUpdateDTO) {
        Long cityId = schoolUpdateDTO.getCityId();
        City city = cityRepository.findById(cityId).get();
        School school = schoolRepository.save(SchoolMapper.mapToSchool(schoolUpdateDTO, city));
        return SchoolMapper.mapToSchoolDTO(school);
    }

    public List<SchoolDTO> getAllSchools() {
        return schoolRepository
                .findAll()
                .stream()
                .map(SchoolMapper::mapToSchoolDTO)
                .collect(Collectors.toList());
    }

    public SchoolDTO getSchoolById(long id) throws ApiBadRequestException {
        try {
            School school = schoolRepository.findById(id).orElse(null);
            return SchoolMapper.mapToSchoolDTO(school);
        }catch (Exception e){
            throw new ApiBadRequestException("No schools with " + id + " id found");
        }
    }

    public SchoolDTO deleteSchoolById(long id) throws ApiBadRequestException {
        try {
        School school = schoolRepository.findById(id).orElse(null);
        schoolRepository.deleteById(id);
        return SchoolMapper.mapToSchoolDTO(school);
        }catch (Exception e){
            throw new ApiBadRequestException("No schools with " + id + " id found");
        }
    }

    @Transactional
    public SchoolDTO updateSchool(SchoolUpdateDTO schoolUpdateDTO) throws ApiBadRequestException {
        try {
        Long schoolId = schoolUpdateDTO.getId();
        School school = schoolRepository.findById(schoolId).get();

        if (schoolUpdateDTO.getName() != null){
            school.setName(schoolUpdateDTO.getName());
        }
        if (schoolUpdateDTO.getUsers() != null){
            school.setUsers(schoolUpdateDTO.getUsers());
        }
        if(schoolUpdateDTO.getCityId() != null) {
            City city = cityRepository.findById(schoolUpdateDTO.getCityId()).get();
            school.setCity(city);
        }

        school = schoolRepository.save(school);
        return SchoolMapper.mapToSchoolDTO(school);
        }catch (Exception e){
            throw new ApiBadRequestException("No schools with " + schoolUpdateDTO.getId() + " id found");
        }
    }

}
