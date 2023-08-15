package com.example.springboot_task.service;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.dto.request.CityUpdateDTO;
import com.example.springboot_task.dto.response.CityDTO;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.dto.response.base.OffsetBasedPageRequest;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.mapping.CityMapper;
import com.example.springboot_task.repository.CityRepository;
import com.example.springboot_task.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final SchoolRepository schoolRepository;

    public CityDTO createCity(CityUpdateDTO cityUpdateDTO) {
        City city = cityRepository.save(CityMapper.mapToCity(cityUpdateDTO));
        return CityMapper.mapToCityDTO(city);
    }

    public ResponseDto<CityDTO> getAllCities(int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<CityDTO> cityDTOList = cityRepository
                .findAll(pageable)
                .stream()
                .map(CityMapper::mapToCityDTO)
                .toList();
        long total = cityRepository
                .count();
        ResponseDto<CityDTO> responseDto = new ResponseDto<>(cityDTOList, total);
        return responseDto;
    }

    public CityDTO getCityById(long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ApiBadRequestException("No cities with " + id + " id found"));
        return CityMapper.mapToCityDTO(city);
    }

    public CityDTO getCityByName(String name) {
        City city = cityRepository.getCityByName(name);
        if (city == null) {
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
        city = cityRepository.save(city);
        return CityMapper.mapToCityDTO(city);
    }

}
