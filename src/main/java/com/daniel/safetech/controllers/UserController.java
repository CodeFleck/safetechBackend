package com.daniel.safetech.controllers;

import com.daniel.safetech.enitities.User;
import com.daniel.safetech.repositories.UserRepository;
import com.daniel.safetech.services.ProductService;
import com.daniel.safetech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setProductService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.listAllUsers();
    }

    @PostMapping("/add")
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @DeleteMapping(path = { "/{id}" })
    public User deleteUser(@PathVariable("id") Integer id) throws Exception {
        User user = userService.getUserById(id).orElseThrow(() -> new Exception("User not found - " + id));
        userService.deleteUserById(id);
        return user;
    }
}