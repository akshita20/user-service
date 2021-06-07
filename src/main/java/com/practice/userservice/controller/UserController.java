package com.practice.userservice.controller;

import com.practice.userservice.entity.User;
import com.practice.userservice.exception.UserNotFoundException;
import com.practice.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "id") int userId) throws UserNotFoundException {
        User user = userService.getUser(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Update user entity.
     *
     * @param userId the user id
     * @param user   the user details
     * @return the response entity
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable(value = "id") Integer userId, @Valid @RequestBody User user) throws UserNotFoundException {
        User updatedUser = userService.updateUser(userId, user)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Delete user.
     *
     * @param userId the user id
     * @return the response entity with HTTP status
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(value = "id") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
