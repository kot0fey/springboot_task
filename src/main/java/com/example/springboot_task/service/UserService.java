package com.example.springboot_task.service;

import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.base.OffsetBasedPageRequest;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.mapping.UserMapper;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.springboot_task.exceptions.ApiBadRequestException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<UserDTO> userDTOList = userRepository
                .findAll(pageable)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .toList();
        long total = userRepository
                .count();
        ResponseDto responseDto = new ResponseDto<>(userDTOList, total);
        return responseDto;
    }


    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiBadRequestException("No user with " + id + " id found."));

        return UserMapper.mapToUserDTO(user);
    }

    public ResponseDto<UserDTO> getUserByName(String name, int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<UserDTO> userDTOList = userRepository
                .findByName(name, pageable)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .toList();
        int total = userRepository
                .countByName(name);
        ResponseDto<UserDTO> responseDto = new ResponseDto<>(userDTOList, total);
        return responseDto;
    }

    public ResponseDto<UserDTO> getUserBySurname(String surname, int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<UserDTO> userDTOList = userRepository
                .findBySurname(surname, pageable)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .toList();
        long total = userRepository
                .countBySurname(surname);
        ResponseDto responseDto = new ResponseDto<>(userDTOList, total);
        return responseDto;
    }

    public ResponseDto<UserDTO> getUserBySchoolId(Long schoolId, int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<UserDTO> userDTOList = userRepository
                .findBySchoolId(schoolId, pageable)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .toList();
        long total = userRepository
                .countBySchoolId(schoolId);
        ResponseDto responseDto = new ResponseDto<>(userDTOList, total);
        return responseDto;
    }

    public ResponseDto<UserDTO> getUserByCityId(Long cityId, int offset, int limit) {
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<UserDTO> userDTOList = userRepository
                .findBySchool_CityId(cityId, pageable)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .toList();
        long total = userRepository
                .countBySchool_CityId(cityId);
        ResponseDto responseDto = new ResponseDto<>(userDTOList, total);
        return responseDto;
    }

    public UserDTO deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiBadRequestException("No users with " + id + " id found"));
        userRepository.deleteById(id);
        return UserMapper.mapToUserDTO(user);

    }

    @Transactional
    public UserDTO updateUser(UserUpdateDTO userUpdateDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiBadRequestException("No users with " + userId + " id found"));

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

    public ResponseDto<UserDTO> getUsersByFilter(String name, String surname, Long schoolId, Long cityId, int offset, int limit) {

//            Page<User> fullList = userRepository.findByFilter(name, surname, schoolId, cityId);
//            return new ResponseDto(fullList.stream().map(u -> UserMapper.mapToUserDTO(u)).toList(), limit, offset);
        Pageable pageable = new OffsetBasedPageRequest(offset, limit);
        List<UserDTO> userDTOList = userRepository
                .findByFilter(name, surname, schoolId, cityId, pageable)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .toList();
        long total = userRepository
                             .countByFilter(name, surname, schoolId, cityId);
        ResponseDto<UserDTO> responseDto = new ResponseDto<>(userDTOList, total);
        return responseDto;
    }
}
