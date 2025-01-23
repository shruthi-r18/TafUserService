package com.backendExam.TafUserService.Models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long userId;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
