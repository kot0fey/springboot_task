package com.example.springboot_task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "teachers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native_teacher"
    )
    @GenericGenerator(
            name = "native_teacher",
            strategy = "native"
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
