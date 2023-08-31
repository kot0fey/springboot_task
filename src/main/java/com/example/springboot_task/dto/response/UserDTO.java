package com.example.springboot_task.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private long id;
    private String name;
    private String surname;
    private int age;
    private String schoolName;

}
