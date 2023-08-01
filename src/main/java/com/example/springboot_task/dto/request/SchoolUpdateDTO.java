package com.example.springboot_task.dto.request;

import com.example.springboot_task.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class SchoolUpdateDTO {
    private Long id;
    private String name;
    private List<User> users;
    private Long cityId;
}
