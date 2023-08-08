package com.example.springboot_task.service;

import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.ResponseDto;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.mapping.UserMapper;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.springboot_task.exceptions.ApiBadRequestException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public UserDTO createUser(UserUpdateDTO userUpdateDTO) {
            Long schoolId = userUpdateDTO.getSchoolId();
            School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new ApiBadRequestException("No school with " + userUpdateDTO.getSchoolId() + " id found."));
            User user = userRepository.save(UserMapper.mapToUser(userUpdateDTO, school));
            return UserMapper.mapToUserDTO(user);
    }

    public ResponseDto<UserDTO> getAllUsers(Integer offset, Integer limit) {
        List fullList = userRepository.findAll().stream().map(UserMapper::mapToUserDTO).collect(Collectors.toList());
        ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
        return responseDto;
    }


    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiBadRequestException("No user with " + id + " id found."));

        return UserMapper.mapToUserDTO(user);
    }

    public ResponseDto<UserDTO> getUserByName(String name, Integer limit, Integer offset) {
            List<UserDTO> fullList = userRepository
                    .findByName(name)
                    .stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toList());
            ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
            return responseDto;
    }

    public ResponseDto<UserDTO> getUserBySurname(String surname, Integer limit, Integer offset) {
            List<UserDTO> fullList = userRepository
                    .findBySurname(surname)
                    .stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toList());
            ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
            return responseDto;
    }

    public ResponseDto<UserDTO> getUserBySchoolId(Long schoolId, Integer limit, Integer offset) {
            List<UserDTO> fullList = userRepository
                    .findBySchoolId(schoolId)
                    .stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toList());
            ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
            return responseDto;
    }

    public ResponseDto<UserDTO> getUserByCityId(Long cityId, Integer limit, Integer offset) {
            List fullList = userRepository
                    .findBySchool_CityId(cityId)
                    .stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toList());
            ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
            return responseDto;
    }

    public UserDTO deleteUserById(Long id) {
            User user = userRepository.findById(id)
                    .orElseThrow(() ->  new ApiBadRequestException("No users with " + id + " id found"));
            userRepository.deleteById(id);
            return UserMapper.mapToUserDTO(user);

    }

    @Transactional
    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) {
            Long userId = userUpdateDTO.getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ApiBadRequestException("No users with " + userUpdateDTO.getId() + " id found"));

            if (userUpdateDTO.getName() != null) {
                user.setName(userUpdateDTO.getName());
            }
            if (userUpdateDTO.getSurname() != null) {
                user.setSurname(userUpdateDTO.getSurname());
            }
            if (userUpdateDTO.getAge() != null) {
                user.setAge(userUpdateDTO.getAge());
            }
            if (userUpdateDTO.getPhone() != null) {
                user.setPhone(userUpdateDTO.getPhone());
            }
            if (userUpdateDTO.getSchoolId() != null) {
                School school = schoolRepository.findById(userUpdateDTO.getSchoolId())
                        .orElseThrow(() -> new ApiBadRequestException("No schools with " + userUpdateDTO.getSchoolId() + " id found"));
                user.setSchool(school);
            }
            user = userRepository.save(user);
            return UserMapper.mapToUserDTO(user);
    }

    public ResponseDto<UserDTO> getUsersByFilter(String name, String surname, Long schoolId, Long cityId, Integer limit, Integer offset) {

//            Page<User> fullList = userRepository.findByFilter(name, surname, schoolId, cityId);
//            return new ResponseDto(fullList.stream().map(u -> UserMapper.mapToUserDTO(u)).toList(), limit, offset);

        return null;
    }
}
