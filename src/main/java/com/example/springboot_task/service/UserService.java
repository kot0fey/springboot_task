package com.example.springboot_task.service;

import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.mapping.UserMapper;
import com.example.springboot_task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserUpdateDTO userDTO) {
         return UserMapper.mapToUserDTO(userRepository.save(
                new User(
                        userDTO.getName(),
                        userDTO.getSurname(),
                        userDTO.getAge(),
                        userDTO.getPhone()
                )));
    }


    public List<UserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
    }


    public UserDTO getUserById(Long id) {
        return UserMapper.mapToUserDTO(userRepository.findById(id).get());
    }

    public UserDTO deleteUserById(Long id) {
        User user = userRepository.findById(id).get();
        userRepository.deleteById(id);
        return UserMapper.mapToUserDTO(user);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserUpdateDTO userDTO) {
        return UserMapper.mapToUserDTO(userRepository.save(
                new User(
                        id,
                        userDTO.getName(),
                        userDTO.getSurname(),
                        userDTO.getAge(),
                        userDTO.getPhone()
                )));
    }
}
