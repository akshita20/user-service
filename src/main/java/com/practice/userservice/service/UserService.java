package com.practice.userservice.service;

import com.practice.userservice.entity.User;
import com.practice.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
//        User user1 = User.builder().id(1).firstName("Dummy1").lastName("").email("abc@gmail.com").build();
//        User user2 = User.builder().id(2).firstName("Dummy2").lastName("").email("abc@gmail.com").build();
//        return Arrays.asList(user1,user2);
    }

    @Override
    public Optional<User> getUser(int userId) {
        return userRepository.findById(userId);
//        Optional<User> user1 = Optional.of(User.builder().id(1).firstName("Dummy1").lastName("").email("abc@gmail.com").build());
//        return user1;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(int userId, User user) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (!existingUser.isPresent())
            return existingUser;
        User updateUser = existingUser.get();
        updateUser.setEmail(user.getEmail());
        updateUser.setLastName(user.getLastName());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(updateUser);
        return Optional.of(updateUser);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }


}
