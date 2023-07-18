package com.example.springboot_task.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user){
        userRepository.save(user);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User with id" + "doesn't exist"));
    }

    public User deleteUserById(Long id) {
        User user = getUserById(id);
        userRepository.deleteById(id);
        return user;
    }

    @Transactional
    public void updateUser(Long id, String name, String surname, int age) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User with id" + "doesn't exist"));

        if (name != null && name.length() > 0 && !Objects.equals(name, user.getName())){
            user.setName(name);
        }

        if (surname != null && surname.length() > 0 && !Objects.equals(surname, user.getSurname())){
            user.setSurname(surname);
        }

        if (!Objects.equals(age, user.getAge())){
            user.setAge(age);
        }
    }
}
