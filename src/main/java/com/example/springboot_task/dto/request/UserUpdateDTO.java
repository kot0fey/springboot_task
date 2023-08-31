package com.example.springboot_task.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDTO {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Surame is mandatory")
    private String surname;
    @NotBlank(message = "Age is mandatory")
    private Integer age;
    private String phone;
    private String avatarUrl;
    private Long schoolId;

}
