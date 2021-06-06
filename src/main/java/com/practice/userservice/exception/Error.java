package com.practice.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Error {
    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
