package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.dto.request.CityUpdateDTO;
import com.example.springboot_task.dto.response.CityDTO;

import java.util.List;
import java.util.Set;

public class CityMapper {

    public static CityDTO mapToCityDTO(City city) {
        return new CityDTO(
                city.getId(),
                city.getName(),
                city.getSchoolNames()
        );
    }

    public static City mapToCity(CityUpdateDTO cityDTO) {
        return new City(
                cityDTO.getId(),
                cityDTO.getName()
        );
    }

}
