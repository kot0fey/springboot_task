package com.example.springboot_task.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
    //@NotBlank(message = "Name is mandatory")
    private String name;
  //  @NotBlank(message = "Surame is mandatory")
    private String surname;
//    @NotBlank(message = "Age is mandatory")
    private Integer age;
    private String phone;
    private String avatarUrl;
    private Long schoolId;

}
