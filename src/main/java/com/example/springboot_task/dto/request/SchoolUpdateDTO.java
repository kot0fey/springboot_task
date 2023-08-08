package com.example.springboot_task.dto.request;

import com.example.springboot_task.domain.Book;
import com.example.springboot_task.domain.Teacher;
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
    private List<Long> usersId;
    private List<Long> teachersId;
    private List<Long> booksId;
    private Long cityId;
}
