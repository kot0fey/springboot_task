package com.example.springboot_task.service;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.dto.request.CityUpdateDTO;
import com.example.springboot_task.dto.response.CityDTO;
import com.example.springboot_task.dto.response.ResponseDto;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.CityMapper;
import com.example.springboot_task.repository.CityRepository;
import com.example.springboot_task.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final SchoolRepository schoolRepository;

    public CityDTO createCity(CityUpdateDTO cityUpdateDTO) {
            List schoolIdList = cityUpdateDTO.getSchoolsId();
            List schoolList = schoolRepository.findAllById(schoolIdList);
            City city = cityRepository.save(CityMapper.mapToCity(cityUpdateDTO, schoolList));
            return CityMapper.mapToCityDTO(city);
    }

    public ResponseDto<CityDTO> getAllCities(Integer limit, Integer offset) {
        List fullList = cityRepository
                .findAll()
                .stream()
                .map(CityMapper::mapToCityDTO)
                .collect(Collectors.toList());
        ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
        return responseDto;
    }

    public CityDTO getCityById(long id) {
            City city = cityRepository.findById(id)
                    .orElseThrow(() -> new ApiBadRequestException("No cities with " + id + " id found"));
            return CityMapper.mapToCityDTO(city);
    }

    public CityDTO getCityByName(String name) {
            City city = cityRepository.getCityByName(name);
            if (city == null){
                throw new ApiBadRequestException("No cities with " + name + " name found");
            }
            return CityMapper.mapToCityDTO(city);
    }

    public CityDTO deleteCityById(long id) {
            City city = cityRepository.findById(id)
                    .orElseThrow(() -> new ApiBadRequestException("No cities with " + id + " id found"));
            cityRepository.deleteById(id);
            return CityMapper.mapToCityDTO(city);
    }

    @Transactional
    public CityDTO updateCity(CityUpdateDTO cityUpdateDTO) {
            Long cityId = cityUpdateDTO.getId();
            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new ApiBadRequestException("No cities with " + cityUpdateDTO.getId() + " id found"));
            String cityName = cityUpdateDTO.getName();
            if (!cityName.isEmpty()) {
                city.setName(cityName);
            }
            if (cityUpdateDTO.getSchoolsId() != null) {
                    List schoolIdList = cityUpdateDTO.getSchoolsId();
                    List schoolList = schoolRepository.findAllById(schoolIdList);
                    city.setSchools(schoolList);
            }
            city = cityRepository.save(city);
            return CityMapper.mapToCityDTO(city);
    }

}
