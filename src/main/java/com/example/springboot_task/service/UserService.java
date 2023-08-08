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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    public UserDTO createUser(UserUpdateDTO userUpdateDTO) {
        try {
            Long schoolId = userUpdateDTO.getSchoolId();
            School school = schoolRepository.findById(schoolId).get();
            User user = userRepository.save(UserMapper.mapToUser(userUpdateDTO, school));
            return UserMapper.mapToUserDTO(user);
        }catch (Exception e){
            throw new ApiBadRequestException("No school with " + userUpdateDTO.getSchoolId() + " id found.");
        }
    }

    public ResponseDto<UserDTO> getAllUsers(Integer offset, Integer limit) {

        List fullList = userRepository.findAll().stream().map(UserMapper::mapToUserDTO).collect(Collectors.toList());
        ResponseDto responseDto = new ResponseDto<>(fullList, limit, offset);
        return responseDto;

    }


    public UserDTO getUserById(Long id) throws ApiBadRequestException {
        try {
            User user = userRepository.findById(id).get();
            return UserMapper.mapToUserDTO(user);
        } catch (Exception e){
            throw new ApiBadRequestException("No user with " + id + " id found.");
        }

    }

    public List<UserDTO> getUserByName(String name) throws ApiBadRequestException {
        try {
            return userRepository
                    .findByName(name)
                    .stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new ApiBadRequestException("No users with \"" + name + "\" name found");
        }
    }

    public List<UserDTO> getUserBySurname(String surname) throws ApiBadRequestException {
        try {
            return userRepository
                    .findBySurname(surname)
                    .stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new ApiBadRequestException("No users with \"" + surname + "\" surname found");
        }
    }

    public List<UserDTO> getUserBySchoolId(long schoolId) throws ApiBadRequestException {
        try {
            return userRepository
                    .findBySchoolId(schoolId)
                    .stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new ApiBadRequestException("No users with " + schoolId + " school id found");
        }
    }

    public List<UserDTO> getUserByCityId(long cityId) throws ApiBadRequestException {
        try {
            return userRepository
                    .findBySchool_CityId(cityId)
                    .stream()
                    .map(UserMapper::mapToUserDTO)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new ApiBadRequestException("No users with " + cityId + " city id found");

        }
    }

    public UserDTO deleteUserById(Long id) throws ApiBadRequestException {
        try {
            User user = userRepository.findById(id).get();
            userRepository.deleteById(id);
            return UserMapper.mapToUserDTO(user);
        } catch (Exception e){
            throw new ApiBadRequestException("No users with " + id + " id found");
        }
    }

    @Transactional
    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) throws ApiBadRequestException {
        try {
            Long userId = userUpdateDTO.getId();
            User user = userRepository.findById(userId).get();

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
                School school = schoolRepository.findById(userUpdateDTO.getSchoolId()).get();
                user.setSchool(school);
            }

            user = userRepository.save(user);
            return UserMapper.mapToUserDTO(user);
        }catch (Exception e){
            throw new ApiBadRequestException("No users with " + userUpdateDTO.getId() + " id found");
        }
    }

    public ResponseDto getUsersByFilter(String name, String surname, Long schoolId, Long cityId, Integer limit, Integer offset) {

        try {
        List fullList = userRepository.findByFilter(name, surname, schoolId, cityId);
        return new ResponseDto(fullList, limit, offset);
        }   catch (Exception e){
            throw new ApiBadRequestException("Bad filter parameter");
        }
    }
}
