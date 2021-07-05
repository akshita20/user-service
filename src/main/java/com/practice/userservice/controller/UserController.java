package com.practice.userservice.controller;

import com.practice.userservice.config.SwaggerConfig;
import com.practice.userservice.entity.User;
import com.practice.userservice.exception.UserNotFoundException;
import com.practice.userservice.service.IUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = { SwaggerConfig.USER_TAG})
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
    @ApiOperation(value = "View list of users", response = List.class)
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
    @ApiOperation(value = "Retrieves a user by the given ID.", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "User not found. ")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUsersById(
            @ApiParam(name="id", value = "The ID of the user.", required = true)
            @PathVariable(value = "id") int userId) throws UserNotFoundException {
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
    @ApiOperation(value = "Creates a new user.", response = User.class)
    @PostMapping("/users")
    public ResponseEntity<User> createUser(
            @ApiParam(name="user", value = "The user.", required = true)
            @Valid @RequestBody User user) {
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
    @ApiOperation(value = "Updates an existing user.",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "User not found. ")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @ApiParam(name="id", value = "The ID of the user.", required = true)
            @PathVariable(value = "id") Integer userId,
            @ApiParam(name="user", value = "The user.", required = true)
            @Valid @RequestBody User user) throws UserNotFoundException {
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
    @ApiOperation("Deletes the user.")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(
            @ApiParam(name="id", value = "The ID of the user.", required = true)
            @PathVariable(value = "id") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
