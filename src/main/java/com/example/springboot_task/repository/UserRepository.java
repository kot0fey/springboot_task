package com.example.springboot_task.repository;

import com.example.springboot_task.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);

    List<User> findBySurname(String surname);

    List<User> findBySchoolId(long schoolId);

    List<User> findBySchool_CityId(long cityId);

}
