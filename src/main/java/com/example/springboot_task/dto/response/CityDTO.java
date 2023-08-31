package com.example.springboot_task.dto.response;

import com.example.springboot_task.domain.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityDTO {
    private long id;
    private String name;
    private List<String> schoolName;
}
