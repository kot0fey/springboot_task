package com.example.springboot_task.repository;

import com.example.springboot_task.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name, Pageable pageable);

    Integer countByName(String name);

    List<User> findBySurname(String surname, Pageable pageable);

    Integer countBySurname(String surname);

    List<User> findBySchoolId(long schoolId, Pageable pageable);

    Integer countBySchoolId(long schoolId);

    List<User> findBySchool_CityId(long cityId, Pageable pageable);

    Integer countBySchool_CityId(long cityId);

//    @Query(value = """
//            SELECT * FROM users
//            JOIN schools ON users.school_id = schools.id
//            WHERE (:name IS NULL OR users.name = :name)
//            AND (:surname IS NULL OR users.surname = :surname)
//            AND (:schoolId IS NULL OR schools.id = :schoolId)
//            AND (:cityId IS NULL OR schools.city_id = :cityId)
//            """, nativeQuery = true)

    @Query(value = """
            SELECT u FROM User u
            JOIN School s ON u.school.id = s.id
            WHERE (:name IS NULL OR u.name = :name)
            AND (:surname IS NULL OR u.surname = :surname)
            AND (:schoolId IS NULL OR s.id = :schoolId)
            AND (:cityId IS NULL OR s.city.id = :cityId)
            """)
    List<User> findByFilter(@Param("name") String name,
                            @Param("surname") String surname,
                            @Param("schoolId") Long schoolId,
                            @Param("cityId") Long cityId,
                            Pageable pageable);


    @Query(value = """
            SELECT COUNT(*)  FROM users
            JOIN schools ON users.school_id = schools.id
            WHERE (:name IS NULL OR users.name = :name)
            AND (:surname IS NULL OR users.surname = :surname)
            AND (:schoolId IS NULL OR schools.id = :schoolId)
            AND (:cityId IS NULL OR schools.city_id = :cityId)
            """, nativeQuery = true)
    Long countByFilter(@Param("name") String name,
                       @Param("surname") String surname,
                       @Param("schoolId") Long schoolId,
                       @Param("cityId") Long cityId);

    List<User> findByPhone(String phone);
}
