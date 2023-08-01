package com.example.springboot_task.service;

import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.mapping.UserMapper;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public UserDTO createUser(UserUpdateDTO userUpdateDTO) {
        School school = schoolRepository.findById(userUpdateDTO.getSchoolId()).orElse(null);
        User user = userRepository.save(UserMapper.mapToUser(userUpdateDTO, school));
        return UserMapper.mapToUserDTO(user);
    }

    // add Limit and Offset - Pagination
    public Page<UserDTO> getAllUsers(Integer offset, Integer limit) {
        return userRepository
                .findAll(PageRequest.of(offset, limit))
                .map(UserMapper::mapToUserDTO);

    }


    public UserDTO getUserById(Long id) {
        return UserMapper.mapToUserDTO(userRepository.findById(id).orElse(null));
    }

    public List<UserDTO> getUserByName(String name) {
        return userRepository
                .findByName(name)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUserBySurname(String surname) {
        return userRepository
                .findBySurname(surname)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUserBySchoolId(long schoolId) {
        return userRepository
                .findBySchoolId(schoolId)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUserByCityId(long cityId) {
        return userRepository
                .findBySchool_CityId(cityId)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO deleteUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        userRepository.deleteById(id);
        return UserMapper.mapToUserDTO(user);
    }

    @Transactional
    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) {
        Long userId = userUpdateDTO.getId();
        User user = userRepository.findById(userId).get();

        if (userUpdateDTO.getName()!= null) {
            user.setName(userUpdateDTO.getName());
        }
        if (userUpdateDTO.getSurname()!= null) {
            user.setSurname(userUpdateDTO.getSurname());
        }
        if (userUpdateDTO.getAge() != null) {
            user.setAge(userUpdateDTO.getAge());
        }
        if (userUpdateDTO.getPhone()!= null) {
            user.setPhone(userUpdateDTO.getPhone());
        }
        if (userUpdateDTO.getSchoolId() != null) {
            School school = schoolRepository.findById(userUpdateDTO.getSchoolId()).get();
            user.setSchool(school);
        }

        user = userRepository.save(user);
        return UserMapper.mapToUserDTO(user);
    }
}
