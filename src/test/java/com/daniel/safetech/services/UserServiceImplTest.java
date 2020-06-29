package com.daniel.safetech.services;

import com.daniel.safetech.enitities.User;
import com.daniel.safetech.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserServiceImpl userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        users.add(createUser());
        users.add(createUser());

        Mockito.when(userRepository.findAll())
                .thenReturn(users);
        Mockito.when(userRepository.findById(any()))
                .thenReturn(java.util.Optional.of(createUser()));
        Mockito.when(userRepository.save(any()))
                .thenReturn(createUser());
    }

    @Test
    public void shouldListALlUsers() {
        List<User> userList = userService.listAllUsers();

        assertThat(userList.size(), Matchers.greaterThan(0));
    }

    @Test
    public void shouldGetUserById() {
        Optional<User> user = userService.getUserById(1);

        assertThat(user.get().getId(), Matchers.equalTo(1));
    }

    @Test
    public void shouldSaveUser() {
        User user = new User();
        user.setName("user");

        User savedUser = userService.saveUser(user);

        assertThat(savedUser.getId(), Matchers.notNullValue());
    }

    private User createUser() {
        User user = new User();
        user.setId(1);
        user.setName("User 1");
        user.setType("admin");
        user.setPassword("password");
        return user;
    }

    private Boolean returnTrue() {
        return true;
    }
}
