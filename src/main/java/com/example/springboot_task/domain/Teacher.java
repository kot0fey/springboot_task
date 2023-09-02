package com.example.springboot_task.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "teachers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native_teacher"
    )
    @SequenceGenerator(
            name = "native_teacher",
            sequenceName = "native",
            allocationSize = 1
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Integer learningExperience;
    @Column(nullable = false)
    private String subject;
    @ManyToOne
    @JoinColumn(name = "schoolId", nullable = true)
    private School school;

}
