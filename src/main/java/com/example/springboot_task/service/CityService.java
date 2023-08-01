package com.example.springboot_task.service;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.dto.request.CityUpdateDTO;
import com.example.springboot_task.dto.response.CityDTO;
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
        City city = cityRepository.save(CityMapper.mapToCity(cityUpdateDTO));
        return CityMapper.mapToCityDTO(city);
    }

    public List<CityDTO> getAllCities() {
        return cityRepository
                .findAll()
                .stream()
                .map(CityMapper::mapToCityDTO)
                .collect(Collectors.toList());
    }

    public CityDTO getCityById(long id) {
        City city = cityRepository.findById(id).orElse(null);
        return CityMapper.mapToCityDTO(city);
    }

    public CityDTO getCityByName(String name) {
        City city = cityRepository.getCityByName(name);
        return CityMapper.mapToCityDTO(city);
    }

    public CityDTO deleteCityById(long id) {
        City city = cityRepository.findById(id).orElse(null);
        cityRepository.deleteById(id);
        return CityMapper.mapToCityDTO(city);
    }

    @Transactional
    public CityDTO updateCity(CityUpdateDTO cityUpdateDTO) {
        /*City city = cityRepository.save(CityMapper.mapToCity(cityUpdateDTO));
        return CityMapper.mapToCityDTO(city);*/

        Long cityId = cityUpdateDTO.getId();
        City city = cityRepository.findById(cityId).get();

        String cityName = cityUpdateDTO.getName();
        if(!cityName.isEmpty()){
            city.setName(cityName);
        }

        if(cityUpdateDTO.getSchools()!=null){
           city.setSchools(cityUpdateDTO.getSchools());
        }

        city = cityRepository.save(city);
        return CityMapper.mapToCityDTO(city);

    }

}
