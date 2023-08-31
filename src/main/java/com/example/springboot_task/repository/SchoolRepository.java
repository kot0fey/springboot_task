package com.example.springboot_task.repository;

import com.example.springboot_task.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    @Query(value = """
            SELECT schools.*, COUNT(users.id) AS users_count
            FROM schools
            LEFT JOIN users ON schools.id = users.school_id
            GROUP BY schools.id
            ORDER BY users_count DESC
            """, nativeQuery = true)
    List<School> findRankedByUsers(Pageable pageable);
}
