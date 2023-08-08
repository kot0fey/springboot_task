package com.example.springboot_task.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native_book"
    )
    @GenericGenerator(
            name = "native_book",
            strategy = "native"
    )
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String Author;
    @ManyToOne
    @JoinColumn(name = "schoolId", nullable = false)
    private School school;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User user;
}
