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
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native_school"
    )
    @GenericGenerator(
            name = "native_school",
            strategy = "native"
    )
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "school")
    private List<User> users;
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

    public void addUser(User user){
        users.add(user);
    }

    public void deleteUser(User user){
        users.remove(user);
    }
}
