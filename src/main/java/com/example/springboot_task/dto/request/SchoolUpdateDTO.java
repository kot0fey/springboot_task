package com.example.springboot_task.dto.request;

import com.example.springboot_task.domain.Book;
import com.example.springboot_task.domain.Teacher;
import com.example.springboot_task.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class SchoolUpdateDTO {
    private String name;
    private Long cityId;
}
