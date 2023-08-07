package com.example.springboot_task.controller;

import com.example.springboot_task.dto.request.UserUpdateDTO;
import com.example.springboot_task.dto.response.ResponseDto;
import com.example.springboot_task.dto.response.UserDTO;
import com.example.springboot_task.exceptions.ApiBadRequestException;
import com.example.springboot_task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserUpdateDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<List<UserDTO>> getUserByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
    }

    @GetMapping("surname/{surname}")
    public ResponseEntity<List<UserDTO>> getUserBySurname(@PathVariable("surname") String surname) {
        return new ResponseEntity<>(userService.getUserBySurname(surname), HttpStatus.OK);
    }

    @GetMapping("schoolId/{schoolId}")
    public ResponseEntity<List<UserDTO>> getUserBySchoolId(@PathVariable("schoolId") Long schoolId) {
        return new ResponseEntity<>(userService.getUserBySchoolId(schoolId), HttpStatus.OK);
    }

    @GetMapping("cityId/{cityId}")
    public ResponseEntity<List<UserDTO>> getUserByCityId(@PathVariable("cityId") Long cityId) {
        return new ResponseEntity<>(userService.getUserByCityId(cityId), HttpStatus.OK);
    }

    @GetMapping("filter")
    public ResponseEntity<ResponseDto<UserDTO>> filter(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "surname", required = false) String surname,
                                                       @RequestParam(value = "schoolId", required = false) Long schoolId,
                                                       @RequestParam(value = "cityId", required = false) Long cityId,
                                                       @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                       @RequestParam(value = "limit", defaultValue = "3") Integer limit
                                                       ) {
        return new ResponseEntity<>(userService.getUsersByFilter(name,surname, schoolId, cityId, limit, offset), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<ResponseDto<UserDTO>> getAllUsers(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "3") Integer limit
    ) {
        return new ResponseEntity<>(userService.getAllUsers(offset, limit), HttpStatus.OK);
        //OffsetBased...
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
