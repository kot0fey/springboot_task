package com.example.springboot_task.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String author;
    @ManyToOne
    @JoinColumn(name = "schoolId", nullable = false)
    private School school;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User user;


    public void takeBook(User user){
        this.user = user;
    }

    public void freeBook(){
        user = null;
    }
}
