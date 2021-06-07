package com.practice.userservice.exception;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(int id) {
        super("User not found with id : "+id);
    }
}
