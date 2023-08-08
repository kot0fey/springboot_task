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
    private List<User> users;
    private List<Teacher> teachers;
    private List<Book> books;
    private Long cityId;
}
