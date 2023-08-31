package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.dto.request.CityUpdateDTO;
import com.example.springboot_task.dto.response.CityDTO;

import java.util.List;
import java.util.Set;

public class CityMapper {

    public static CityDTO mapToCityDTO(City city) {
        return CityDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .schoolName(city.getSchoolNames())
                .build();
    }

    public static City mapToCity(CityUpdateDTO cityDTO) {
        return City.builder()
                .name(cityDTO.getName())
                .build();
    }

}
