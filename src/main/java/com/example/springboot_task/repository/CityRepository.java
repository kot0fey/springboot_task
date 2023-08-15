package com.example.springboot_task.repository;

import com.example.springboot_task.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City getCityByName(String cityName);
    Page<City> findAll(Pageable pageable);
}
