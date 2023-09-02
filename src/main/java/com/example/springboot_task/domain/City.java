package com.example.springboot_task.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native_city"
    )
    @SequenceGenerator(
            name = "native_city",
            sequenceName = "native",
            allocationSize = 1
    )
    private long id;

    @Column(nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "city")
    @Setter(AccessLevel.NONE)
    private List<School> schools;

    public City(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<String> getSchoolNames() {
        if (schools == null){
            return null;
        }
        List<String> schoolNames = schools
                .stream()
                .map(School::getName)
                .collect(Collectors.toList());
        return schoolNames;
    }

    public void addSchool(School school){
        schools.add(school);
    }

    public void deleteSchool(School school){
        schools.remove(school);
    }
}
