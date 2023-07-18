package com.example.springboot_task.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String greeting() {
        return "Hello, register your users, please";
    }

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
    }

    @PatchMapping("{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String surname, @RequestParam(required = false) int age) {
        userService.updateUser(id, name, surname, age);
    }
}
