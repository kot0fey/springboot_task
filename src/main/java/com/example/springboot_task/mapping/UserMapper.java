package com.example.springboot_task.mapping;

import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.UserDTO;

public class UserMapper {

    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .schoolName(user.getSchool().getName())
                .build();
    }

    public static User mapToUser(UserUpdateDTO userDTO, School school) {
        return User.builder()
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .age(userDTO.getAge())
                .phone(userDTO.getPhone())
                .avatarUrl(userDTO.getAvatarUrl())
                .school(school)
                .build();
    }

}
