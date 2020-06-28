package com.daniel.safetech.services;

import com.daniel.safetech.enitities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> listAllUsers();

    Optional<User> getUserById(Integer id);

    User saveUser(User user);

    void deleteUserById(Integer id);

    void deleteUser(User user);

}
