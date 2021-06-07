package com.practice.userservice.service;

import com.practice.userservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();

    Optional<User> getUser(int userId);

    User createUser(User user);

    Optional<User> updateUser(int userId, User user);

    void deleteUser(int userId);
}
