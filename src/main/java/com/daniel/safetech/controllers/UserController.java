package com.daniel.safetech.controllers;

import com.daniel.safetech.enitities.User;
import com.daniel.safetech.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/add")
    public void createUser(@RequestBody User user) {
        userRepository.save(user);
    }

}