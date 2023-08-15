package com.example.springboot_task.controller;

import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.base.ResponseDto;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDTO createUser(@RequestBody UserUpdateDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("name/{name}")
    public ResponseDto<UserDTO> getUserByName(@PathVariable("name") String name,
                                              @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                              @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return userService.getUserByName(name, offset, limit);
    }

    @GetMapping("surname/{surname}")
    public ResponseDto<UserDTO> getUserBySurname(@PathVariable("surname") String surname,
                                                 @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                 @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return userService.getUserBySurname(surname, offset, limit);
    }

    @GetMapping("schoolId/{schoolId}")
    public ResponseDto<UserDTO> getUserBySchoolId(@PathVariable("schoolId") Long schoolId,
                                                  @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                  @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return userService.getUserBySchoolId(schoolId, offset, limit);
    }

    @GetMapping("cityId/{cityId}")
    public ResponseDto<UserDTO> getUserByCityId(@PathVariable("cityId") Long cityId,
                                                @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return userService.getUserByCityId(cityId, offset, limit);
    }

    @GetMapping("filter")
    public ResponseDto<UserDTO> filter(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "surname", required = false) String surname,
                                       @RequestParam(value = "schoolId", required = false) Long schoolId,
                                       @RequestParam(value = "cityId", required = false) Long cityId,
                                       @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                       @RequestParam(value = "limit", defaultValue = "3") Integer limit


    ) {
        return userService.getUsersByFilter(name, surname, schoolId, cityId, offset, limit);
    }

    @GetMapping("all")
    public ResponseDto<UserDTO> getAllUsers(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return userService.getAllUsers(offset, limit);
    }

    @PutMapping("{id}")
    public UserDTO updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDTO user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
    }

}
