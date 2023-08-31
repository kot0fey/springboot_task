package com.example.springboot_task.dto.response;

import com.example.springboot_task.domain.User;
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
public class SchoolDTO {
    private Long id;
    private String name;
    private List<String> usersNames;
    private List<String> teachersNames;
    private List<String> booksNames;
    private String cityName;

}
