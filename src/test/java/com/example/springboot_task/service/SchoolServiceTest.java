package com.example.springboot_task.service;


import com.example.springboot_task.domain.City;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.CityUpdateDTO;
import com.example.springboot_task.dto.request.SchoolUpdateDTO;
import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.SchoolDTO;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.dto.response.base.OffsetBasedPageRequest;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.mapping.SchoolMapper;
import com.example.springboot_task.repository.CityRepository;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Testcontainers
@SpringBootTest
public class SchoolServiceTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>();
    @Autowired
    private SchoolService testingService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private CityService cityService;
    @Autowired
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        cityService.createCity(
                CityUpdateDTO.builder()
                        .name("city1")
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        schoolRepository.deleteAll();
        cityRepository.deleteAll();
    }

    //School
    private SchoolUpdateDTO generateSchoolUpdateDto(int additionalNumber) {
        return SchoolUpdateDTO.builder()
                .name("school" + additionalNumber)
                .cityId(cityRepository.getCityByName("city1").getId())
                .build();
    }

    private List<SchoolUpdateDTO> generateSchoolUpdateDtoS(int additionalNumber) {
        List<SchoolUpdateDTO> schoolList = new ArrayList<>();
        for (int i = 1; i <= additionalNumber; i++) {
            schoolList.add(
                    generateSchoolUpdateDto(i)
            );
        }
        return schoolList;
    }

    private List<SchoolDTO> saveGeneratedSchoolDtos(int additionalNumber) {
        List<SchoolUpdateDTO> schoolUpdateDTOS = generateSchoolUpdateDtoS(additionalNumber);
        for (SchoolUpdateDTO school : schoolUpdateDTOS) {
            testingService.createSchool(school);
        }
        List<SchoolDTO> schoolList = schoolUpdateDTOS
                .stream()
                .map(dto -> SchoolMapper.mapToSchoolDTO(schoolRepository.findSchoolByName(dto.getName())))
                .collect(Collectors.toList());
        //schoolRepository.saveAll(schoolList);
        return schoolList;
    }

    //User
    private UserUpdateDTO generateUserUpdateDto(int additionalNumber, int schoolNumber) {
        return UserUpdateDTO.builder()
                .name("user" + additionalNumber + " s" + schoolNumber)
                .surname("user" + additionalNumber + " s" + schoolNumber)
                .age(additionalNumber)
                .phone("user" + additionalNumber + " s" + schoolNumber)
                .avatarUrl("user" + additionalNumber + " s" + schoolNumber)
                .schoolId(schoolRepository.findSchoolByName("school" + schoolNumber).getId())
                .build();
    }

    private List<UserUpdateDTO> generateUserUpdateDtoS(int numberOfUsers, int schoolNumber) {
        List<UserUpdateDTO> userList = new ArrayList<>();
        for (int i = 1; i <= numberOfUsers; i++) {
            UserUpdateDTO user = generateUserUpdateDto(i, schoolNumber);
            userList.add(user);
        }
        return userList;
    }

    private List<UserUpdateDTO> saveGeneratedUserUpdateDtoS(int numberOfUsers, int schoolNumber) {
        List<UserUpdateDTO> userList = generateUserUpdateDtoS(numberOfUsers, schoolNumber);
        for (UserUpdateDTO userUpdateDTO : userList) {
            userService.createUser(userUpdateDTO);
        }
        return userList;
    }

    private ResponseDto<SchoolDTO> listToResponceDto(List dataList) {
        return new ResponseDto(dataList, dataList.size());
    }

//    private UserUpdateDTO mapToUserUpdateDto(User user) {
//        return UserUpdateDTO.builder()
//                .name(user.getName())
//                .surname(user.getSurname())
//                .age(user.getAge())
//                .phone(user.getPhone())
//                .avatarUrl(user.getAvatarUrl())
//                .schoolId(user.getSchool().getId())
//                .build();
//    }


    @Test
    @DisplayName("Rank by users amount")
    void rankByUsers() {
        int numberOfSchools = 5;
        int offset = 0;
        int limit = 5;

        List<School> rawSchoolList = List.of(
                School.builder().name("school5").city(cityRepository.getCityByName("city1")).build(),
                School.builder().name("school4").city(cityRepository.getCityByName("city1")).build(),
                School.builder().name("school3").city(cityRepository.getCityByName("city1")).build(),
                School.builder().name("school2").city(cityRepository.getCityByName("city1")).build(),
                School.builder().name("school1").city(cityRepository.getCityByName("city1")).build()
                );

        schoolRepository.saveAll(
                rawSchoolList
        );


        List<UserUpdateDTO> userList = new ArrayList<>();
        for (int i = 1; i <= numberOfSchools; i++) {
            List<UserUpdateDTO> generatedUsers = saveGeneratedUserUpdateDtoS(i, i);
            userList.addAll(generatedUsers);
        }

        ResponseDto<SchoolDTO> expectedData = listToResponceDto(
                rawSchoolList
                        .stream()
                        .map(SchoolMapper::mapToSchoolDTO)
                        .collect(Collectors.toList())
        );
        ResponseDto<SchoolDTO> actualData = testingService.rankByUsers(offset, limit);

        Assertions.assertAll(
                () -> Assertions.assertFalse(expectedData.getTotal() == 0),
                () -> Assertions.assertEquals(expectedData, actualData)
        );
    }
}
