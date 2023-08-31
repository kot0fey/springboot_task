package com.example.springboot_task.service;

import com.example.springboot_task.domain.*;
import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import com.example.springboot_task.dto.response.base.OffsetBasedPageRequest;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.dto.response.SchoolDTO;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.SchoolMapper;
import com.example.springboot_task.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final CityRepository cityRepository;


    public SchoolDTO createSchool(SchoolUpdateDTO schoolUpdateDTO) {
        Long cityId = schoolUpdateDTO.getCityId();
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new ApiBadRequestException("No city with " + schoolUpdateDTO.getCityId() + " id found."));
        School school = schoolRepository.save(SchoolMapper.mapToSchool(schoolUpdateDTO, city));
        return SchoolMapper.mapToSchoolDTO(school);
    }

    public ResponseDto<SchoolDTO> getAllSchools(int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        var schools = schoolRepository.findAll(pageable);
        List<SchoolDTO> schoolDTOList = schools.get()
                .map(SchoolMapper::mapToSchoolDTO)
                .toList();
        return new ResponseDto<>(schoolDTOList, schools.getTotalElements());
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
        if (schoolUpdateDTO.getCityId() != null) {
            City city = cityRepository.findById(schoolUpdateDTO.getCityId())
                    .orElseThrow(() -> new ApiBadRequestException("No city with " + schoolUpdateDTO.getCityId() + " id found."));
            school.setCity(city);
        }

        school = schoolRepository.save(school);
        return SchoolMapper.mapToSchoolDTO(school);

    }

    // @Query(Select ... Order by )
    public ResponseDto<SchoolDTO> rankByUsers(int offset, int limit) {

        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<SchoolDTO> schoolDTOList = schoolRepository
                .findRankedByUsers(pageable)
                .stream()
                .sorted(Comparator.comparingInt(School::getNumberOfUsers).reversed())
                .map(SchoolMapper::mapToSchoolDTO)
                .toList();

        long total = schoolRepository
                .count();
        return new ResponseDto<>(schoolDTOList, total);
    }
}
