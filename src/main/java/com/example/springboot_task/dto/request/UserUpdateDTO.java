package com.example.springboot_task.dto.request;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String phone;
    private Long schoolId;

}
