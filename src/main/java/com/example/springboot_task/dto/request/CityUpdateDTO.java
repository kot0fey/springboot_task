package com.example.springboot_task.dto.request;

import com.example.springboot_task.domain.School;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CityUpdateDTO {
    private Long id;
    private String name;
    private List<School> schools;
}
