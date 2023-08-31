package com.example.springboot_task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {
    private Long id;
    private String name;
    private String author;
    private Long schoolId;
    private Long userId;
}
