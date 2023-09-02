package com.example.springboot_task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {
    private String name;
    private String author;
    private Long schoolId;
    private Long userId;
}
