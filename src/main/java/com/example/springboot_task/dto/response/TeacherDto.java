package com.example.springboot_task.dto.response;

import com.example.springboot_task.domain.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDto {
    private Long id;
    private String name;
    private String surname;
    private Integer learningExperience;
    private String subject;
    private String schoolName;
}
