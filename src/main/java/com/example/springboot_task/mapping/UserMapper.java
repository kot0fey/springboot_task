package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.UserDTO;

public class UserMapper {
    public static UserDTO mapToUserDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getAge()
        );
    }

    public static User mapToUser(UserUpdateDTO userDTO){
        return new User(
                userDTO.getName(),
                userDTO.getSurname(),
                userDTO.getAge(),
                userDTO.getPhone()
        );
    }

}
