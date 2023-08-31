package com.example.springboot_task.dto.request;

import com.example.springboot_task.domain.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherUpdateDto {
    private String name;
    private String surname;
    private Integer age;
    private Integer learningExperience;
    private String subject;
    private Long schoolId;

}
