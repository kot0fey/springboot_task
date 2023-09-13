package com.example.springboot_task.dto.request;

import com.example.springboot_task.domain.School;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CityUpdateDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;
}
