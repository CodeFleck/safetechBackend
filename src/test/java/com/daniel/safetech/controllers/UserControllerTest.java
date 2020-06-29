package com.daniel.safetech.controllers;

import com.daniel.safetech.enitities.User;
import com.daniel.safetech.services.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    UserServiceImpl mockUserService;

    @Test
    public void shouldGetUsers() throws Exception {
        List<User> users = createUsers();

        Mockito.when(mockUserService.listAllUsers()).thenReturn(users);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users/")
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mvc.perform(builder);
        MvcResult result = resultActions.andReturn();
        String responseAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> itemList = objectMapper.readValue(responseAsString, List.class);

        assertThat(itemList.size(), Matchers.is(2));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"User 1\", \"password\":\"password\", \"type\":\"admin\"}");
        ResultActions resultActions = mvc.perform(builder);
        MvcResult result = resultActions.andReturn();

        assertThat(result.getResponse().getStatus(), Matchers.is(200));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        List<User> users = createUsers();
        Mockito.doNothing().when(mockUserService).deleteUser(any());
        Mockito.when(mockUserService.getUserById(any())).thenReturn(Optional.ofNullable(users.get(0)));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/users/1");
        mvc.perform(builder);
        Mockito.verify(mockUserService, Mockito.times(1)).getUserById(any());
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1);
        user1.setName("User 1");
        user1.setPassword("password");
        user1.setType("admin");

        User user2 = new User();
        user2.setId(2);
        user2.setName("User 2");
        user2.setPassword("password");
        user2.setType("user");

        users.add(user1);
        users.add(user2);

        return users;
    }
}
