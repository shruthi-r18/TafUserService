package com.backendExam.TafUserService.Services.Interface;
import com.backendExam.TafUserService.Models.User;

public interface UserService {

    User add(User user);
    User get(Long userId);
    User update(Long userId , User user);

}
