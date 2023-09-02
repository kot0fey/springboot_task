package com.example.springboot_task.service;

import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.response.base.OffsetBasedPageRequest;
import com.example.springboot_task.mapping.UserMapper;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.awt.SystemColor.desktop;

@Testcontainers
@SpringBootTest
class UserServiceTest {
//    @Container
//    public static PostgreSQLContainer container = new PostgreSQLContainer<>()
//            .withUsername("testingContainer")
//            .withPassword("pass")
//            .withDatabaseName("testingContainer");
//
//    // Spring Boot newer 2.2.6
//    @DynamicPropertySource
//    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", container::getJdbcUrl);
//        registry.add("spring.datasource.password", container::getPassword);
//        registry.add("spring.datasource.username", container::getUsername);
//    }

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>();

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchoolRepository schoolRepository;


    @Test
    void canGetAllUsers() {
        System.out.println("Assalamu Aleikum");
    }

}