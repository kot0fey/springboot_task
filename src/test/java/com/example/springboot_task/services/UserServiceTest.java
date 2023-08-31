package com.example.springboot_task.services;

import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import com.example.springboot_task.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private SchoolRepository schoolRepository;
    @Test
    public void createUserTest(){
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(
                "name",
                "surname",
                1,
                "phone",
                "avatarUrl",
                1L
        );


        UserDTO userDTO = userService.createUser(userUpdateDTO);
        Assert.notNull(userDTO);
    }
}
