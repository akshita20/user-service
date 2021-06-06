package com.practice.userservice.controller;

import com.practice.userservice.entity.User;
import com.practice.userservice.exception.UserNotFoundException;
import com.practice.userservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserControllerTest {


    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    private static final int id1 = 1;
    private static final int id2 = 2;
    private static final String firstName1 = "first";
    private static final String firstName2 = "second";
    private static final String lastName1 = "user1";
    private static final String lastName2 = "user2";
    private static final String email1 = "first@xyz.com";
    private static final String email2 = "second@xyz.com";

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = User.builder().id(id1).firstName(firstName1).lastName(lastName1).email(email1).createdAt(LocalDateTime.now()).build();
        user2 = User.builder().id(id2).firstName(firstName2).lastName(lastName2).email(email2).createdAt(LocalDateTime.now()).build();
    }
    @Test
    void getUsers() {
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userService.getUsers()).thenReturn(users);
        List<User> userResponse = userController.getUsers();
        Assertions.assertNotNull(userResponse.get(0));
        Assertions.assertEquals(id1, userResponse.get(0).getId());
        Assertions.assertEquals(id2, userResponse.get(1).getId());
        Assertions.assertEquals(firstName1, userResponse.get(0).getFirstName());
        Assertions.assertEquals(lastName2, userResponse.get(1).getLastName());
        Assertions.assertEquals(email1,userResponse.get(0).getEmail());
    }

    @Test
    void getUsersById() throws UserNotFoundException {
        Mockito.when(userService.getUser(id1)).thenReturn(Optional.of(user1));
        User userResponse = userController.getUsersById(id1).getBody();
        Assertions.assertEquals(id1, userResponse.getId());
        Assertions.assertEquals(firstName1, userResponse.getFirstName());
        Assertions.assertEquals(lastName1, userResponse.getLastName());
        Assertions.assertEquals(email1,userResponse.getEmail());
    }

    @Test
    void getUsersByIdNotFound(){
        Mockito.when(userService.getUser(id1)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userController.getUsersById(id1);
        });
    }

    @Test
    void createUser() {
        Mockito.when(userService.createUser(user1)).thenReturn(user1);
        User userResponse = userController.createUser(user1).getBody();
        Assertions.assertEquals(user1, userResponse);
    }

    @Test
    void updateUser() throws UserNotFoundException {
        Mockito.when(userService.updateUser(id1, user1)).thenReturn(Optional.of(user1));
        User userResponse = userController.updateUser(id1, user1).getBody();
        Assertions.assertEquals(user1, userResponse);
    }

    @Test
    void updateUserNotFound(){
        Mockito.when(userService.updateUser(id1, user1)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userController.updateUser(id1, user1);
        });
    }

    @Test
    void deleteUser() {
        Mockito.doNothing().when(userService).deleteUser(id1);
        userController.deleteUser(id1);
        Mockito.verify(userService, Mockito.times(1)).deleteUser(id1);
    }
}