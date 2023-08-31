package com.example.springboot_task.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native_user"
    )
    @GenericGenerator(
            name = "native_user",
            strategy = "native"
    )
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private String phone;
    @Column
    private String avatarUrl;
    @Column(nullable = false)
    private Date createdAt = new Date();
    @ManyToOne
    @JoinColumn(name = "schoolId", nullable = false)
    private School school;
    @OneToMany
    @JoinColumn(name = "books")
    @Setter(AccessLevel.NONE)
    private List<Book> books;


    public User(String name, String surname, int age, String phone, String avatarUrl, School school) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.school = school;
    }
}

