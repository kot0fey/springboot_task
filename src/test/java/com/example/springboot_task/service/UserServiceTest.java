


package com.example.springboot_task.service;

import com.example.springboot_task.domain.City;
import com.example.springboot_task.domain.School;
import com.example.springboot_task.domain.User;
import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.dto.response.base.OffsetBasedPageRequest;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.mapping.UserMapper;
import com.example.springboot_task.repository.CityRepository;
import com.example.springboot_task.repository.SchoolRepository;
import com.example.springboot_task.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Testcontainers
@SpringBootTest
class UserServiceTest {

    private static final int NUMBER_OF_USERS = 2;

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService testingService;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private CityRepository cityRepository;


    @BeforeEach
    private void setUp() {
        //Create base cities
        cityRepository.saveAll(
                List.of(
                        City.builder()
                                .name("city1")
                                .build(),
                        City.builder()
                                .name("city2")
                                .build()
                )
        );
        //Create base schools
        schoolRepository.saveAll(
                List.of(
                        School.builder()
                                .name("school1")
                                .city(cityRepository.getCityByName("city1"))
                                .build(),
                        School.builder()
                                .name("school2")
                                .city(cityRepository.getCityByName("city2"))
                                .build()
                )
        );
    }

    @AfterEach
    private void tearDown() {
        userRepository.deleteAll();
        schoolRepository.deleteAll();
        cityRepository.deleteAll();
    }

    private User generateUser(int additionalNumber) {
        return User.builder()
                .name("user" + additionalNumber)
                .surname("user" + additionalNumber)
                .age(additionalNumber)
                .phone("user" + additionalNumber)
                .avatarUrl("user" + additionalNumber)
                .school(schoolRepository.findSchoolByName("school1"))
                .build();
    }

    private List<User> generateUsers(int numberOfUsers) {
        List<User> userList = new ArrayList<>();
        for (int i = 1; i <= numberOfUsers; i++) {
            User user = generateUser(i);
            userList.add(user);
        }
        return userList;
    }

    private List<User> saveGeneratedUsers(int numberOfUsers) {
        List<User> userList = generateUsers(numberOfUsers);
        userRepository.saveAll(userList);
        return userList;
    }

    private ResponseDto<UserDTO> listToResponceDto(List dataList) {
        return new ResponseDto(dataList, dataList.size());
    }


    private UserUpdateDTO mapToUserUpdateDto(User user) {
        return UserUpdateDTO.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .schoolId(user.getSchool().getId())
                .build();
    }


    @Test
    @DisplayName("Creating users")
    void CreateUsers() {
        List<User> userList = generateUsers(NUMBER_OF_USERS);

        List<UserDTO> expectedData = userList
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());

        List<UserUpdateDTO> userUpdateDTO = userList
                .stream()
                .map(u -> mapToUserUpdateDto(u))
                .collect(Collectors.toList());
        List<UserDTO> actualData = userUpdateDTO
                .stream()
                .map(u -> testingService.createUser(u))
                .collect(Collectors.toList());

        Assertions.assertAll(
                () -> Assertions.assertFalse(expectedData.isEmpty()),
                () -> Assertions.assertEquals(
                        expectedData
                                .stream()
                                .map(UserDTO::getName)
                                .collect(Collectors.toList()),
                        actualData.
                                stream()
                                .map(UserDTO::getName)
                                .collect(Collectors.toList())
                ),
                () -> Assertions.assertEquals(
                        expectedData
                                .stream()
                                .map(UserDTO::getSurname)
                                .collect(Collectors.toList()),
                        actualData
                                .stream()
                                .map(UserDTO::getSurname)
                                .collect(Collectors.toList())
                ),
                () -> Assertions.assertEquals(
                        expectedData
                                .stream()
                                .map(UserDTO::getAge)
                                .collect(Collectors.toList()),
                        actualData
                                .stream()
                                .map(UserDTO::getAge)
                                .collect(Collectors.toList())
                ),
                () -> Assertions.assertEquals(
                        expectedData
                                .stream()
                                .map(UserDTO::getSchoolName)
                                .collect(Collectors.toList()),
                        actualData
                                .stream()
                                .map(UserDTO::getSchoolName)
                                .collect(Collectors.toList())
                )

        );
    }


    @Test
    @DisplayName("Get all users")
    void GetAllUsers() {
        List<User> userList = saveGeneratedUsers(NUMBER_OF_USERS);

        List<UserDTO> expectedData = userList
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());
        List<UserDTO> actualData = testingService.getAllUsers(0, NUMBER_OF_USERS).getData();


        Assertions.assertAll(
                () -> Assertions.assertFalse(expectedData.isEmpty()),
                () -> Assertions.assertEquals(expectedData, actualData)
        );
    }

    @Test
    @DisplayName("Get user by ID")
    void GetUserById() {
        List<User> userList = saveGeneratedUsers(NUMBER_OF_USERS);

        List<UserDTO> expectedData = userList
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());

        List<Long> idList = expectedData
                .stream()
                .map(UserDTO::getId)
                .collect(Collectors.toList());

        List<UserDTO> actualData = new ArrayList<>();
        for (Long id : idList) {
            actualData.add(
                    testingService.getUserById(id)
            );
        }
        Assertions.assertAll(
                () -> Assertions.assertFalse(expectedData.isEmpty()),
                () -> Assertions.assertEquals(expectedData, actualData)
        );
    }

    @Test
    @DisplayName("Get user by name")
    void GetUserByName() {

        List<UserDTO> expectedData = saveGeneratedUsers(NUMBER_OF_USERS)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());

        List<String> nameList = expectedData
                .stream()
                .distinct()
                .map(UserDTO::getName)
                .collect(Collectors.toList());

        List<UserDTO> actualData = new ArrayList<>();
        for (String name : nameList) {
            actualData.addAll(
                    testingService.getUserByName(name, 0, NUMBER_OF_USERS).getData()
            );
        }
        actualData
                .stream()
                .distinct()
                .collect(Collectors.toList());

        Assertions.assertAll(
                () -> Assertions.assertFalse(expectedData.isEmpty()),
                () -> Assertions.assertEquals(expectedData, actualData)
        );
    }

    @Test
    @DisplayName("Update user")
    void UpdateUser() {
        User user = saveGeneratedUsers(1)
                .get(0);
        user.setName("POPKA");

        UserDTO expectedData = UserMapper.mapToUserDTO(user);

        Pageable pageable = new OffsetBasedPageRequest(0, 2);
        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
                .name("POPKA")
                .build();
        Long id = user.getId();
        UserDTO actualData = testingService
                .updateUser(userUpdateDTO, id);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(expectedData),
                () -> Assertions.assertEquals(expectedData, actualData)
        );
    }

    @Test
    @DisplayName("Get user by filter")
    void GetUserByFilter() {

        Pageable pageable = new OffsetBasedPageRequest(0, 3);

        List<User> users = List.of(
                User.builder().name("Bob").surname("Marley").age(1).phone("893").avatarUrl("avatar").school(schoolRepository.findSchoolByName("school1")).build(),
                User.builder().name("Bob").surname("Marley").age(1).phone("89").avatarUrl("avatar").school(schoolRepository.findSchoolByName("school2")).build(),
                User.builder().name("Bob").surname("Popkins").age(1).phone("8").avatarUrl("avatar").school(schoolRepository.findSchoolByName("school2")).build()
        );
        userRepository.saveAll(users);

        //1, 2, 3
        ResponseDto<UserDTO> expectedData1 = testingService.getUsersByFilter("Bob", null, null, null, 0, 3);
        ResponseDto<UserDTO> actualData1 = new ResponseDto<>(
                userRepository
                        .findByName("Bob", pageable)
                        .stream()
                        .map(UserMapper::mapToUserDTO)
                        .collect(Collectors.toList()),
                userRepository
                        .countByName("Bob")
        );
        //1, 2
        ResponseDto<UserDTO> expectedData2 = testingService.getUsersByFilter("Bob", "Marley", null, null, 0, 3);
        ResponseDto<UserDTO> actualData2 = new ResponseDto<>(
                userRepository
                        .findBySurname("Marley", pageable)
                        .stream()
                        .map(UserMapper::mapToUserDTO)
                        .collect(Collectors.toList()),
                userRepository
                        .countBySurname("Marley")
        );
        //2, 3
        ResponseDto<UserDTO> expectedData3 = testingService.getUsersByFilter(null, null, schoolRepository.findSchoolByName("school2").getId(), null, 0, 3);
        ResponseDto<UserDTO> actualData3 = new ResponseDto<>(
                userRepository
                        .findBySchoolId(schoolRepository.findSchoolByName("school2").getId(), pageable)
                        .stream()
                        .map(UserMapper::mapToUserDTO)
                        .collect(Collectors.toList()),
                userRepository
                        .countBySchoolId(schoolRepository.findSchoolByName("school2").getId())
        );

        List<ResponseDto<UserDTO>> expectedDataList = List.of(expectedData1, expectedData2, expectedData3);
        List<ResponseDto<UserDTO>> actualDataList = List.of(actualData1, actualData2, actualData3);

        for (int i = 0; i < 3; i++) {
            ResponseDto<UserDTO> expectedData = expectedDataList.get(i);
            ResponseDto<UserDTO> actualData = actualDataList.get(i);
            Assertions.assertAll(
                    () -> Assertions.assertNotNull(expectedData),
                    () -> Assertions.assertEquals(expectedData,actualData)
            );
        }
    }


    @Test
    @DisplayName("Delete users")
    void DeleteUser() {


        List<UserDTO> expectedData = saveGeneratedUsers(NUMBER_OF_USERS)
                .stream()
                .map(UserMapper::mapToUserDTO)
                .collect(Collectors.toList());

        List<UserDTO> actualData = new ArrayList<>();
        for (UserDTO user : expectedData) {
            actualData.add(testingService.deleteUserById(user.getId()));
        }

        Assertions.assertAll(
                () -> Assertions.assertFalse(expectedData.isEmpty()),
                () -> Assertions.assertEquals(expectedData, actualData)
        );

    }


}