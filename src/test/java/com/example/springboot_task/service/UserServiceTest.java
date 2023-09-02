package com.example.springboot_task.service;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.mapping.UserMapper;
import com.example.springboot_task.repository.CityRepository;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest
class UserServiceTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>()
            .withUsername("testingContainer")
            .withPassword("pass")
            .withDatabaseName("testingContainer")
            .withReuse(false);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserService testingService;

    //For BeforeAll
    @Autowired
    private CityRepository cityRepository;
    static City city1;
    static School school1;

    @BeforeEach
    void setUp() {
        city1 = City.builder()
                .name("city1")
                .build();
        cityRepository.save(city1);

        school1 = School.builder()
                .name("school1")
                .city(city1)
                .build();
        schoolRepository.save(school1);
    }

    @Test
    void UserService_CreateUser_Test(){
        int numberOfUsers = 2;
        List<User> usersInBase = new ArrayList<>();
        for (int i = 1; i <= numberOfUsers; i++) {
            User user = User.builder()
                    .name("user" + i)
                    .surname("user" + i)
                    .age(i)
                    .phone("user" + i)
                    .avatarUrl("user" + i)
                    .school(school1)
                    .build();
            usersInBase.add(user);
            userRepository.save(user);
        }
    }

    @Test
    void UserService_GetAllUsers_Test() {
        int numberOfUsers = 2;
        List<User> usersInBase = new ArrayList<>();
        for (int i = 1; i <= numberOfUsers; i++) {
            User user = User.builder()
                    .name("user" + i)
                    .surname("user" + i)
                    .age(i)
                    .phone("user" + i)
                    .avatarUrl("user" + i)
                    .school(school1)
                    .build();
            usersInBase.add(user);
            userRepository.save(user);
        }

        ResponseDto<UserDTO> usersDtoInBase = new ResponseDto<>(usersInBase.stream().map(UserMapper::mapToUserDTO).collect(Collectors.toList()), usersInBase.size());



        ResponseDto<UserDTO> testingList = testingService.getAllUsers(0, numberOfUsers);

        for (int i = 0; i < numberOfUsers; i++) {
            Assert.assertEquals(usersDtoInBase, testingList);
        }
    }

}