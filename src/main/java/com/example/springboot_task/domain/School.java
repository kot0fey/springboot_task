package com.example.springboot_task.domain;

import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schools")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native_school"
    )
    @GenericGenerator(
            name = "native_school",
            strategy = "native"
    )
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "school")
    private List<User> users;
    @OneToMany
    @JoinColumn(name = "teachers", nullable = true)
    private List<Teacher> teachers;
    @OneToMany
    @JoinColumn(name = "books", nullable = true)
    private List<Book> books;
    @ManyToOne
    @JoinColumn(name = "cityId", nullable = false)
    private City city;

    public List<String> getUsersNames(){
        List<String> names = new ArrayList<>();
        for(User user:users){
            names.add(user.getName() + " " + user.getSurname());
        }
        return names;
    }

    public List<String> getTeachersNames(){
        List<String> names = new ArrayList<>();
        for(Teacher teacher:teachers){
            names.add(teacher.getName() + " " + teacher.getSurname());
        }
        return names;
    }

    public List<String> getBooksNames(){
        List<String> names = new ArrayList<>();
        for(Book book:books){
            names.add(book.getName() + " by " + book.getAuthor());
        }
        return names;
    }
}
