package com.example.service;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto myCreateUser(UserRequestDto myUserRequestDto);
    UserResponseDto myGetUserById(Long myUserId);
    List<UserResponseDto> myGetAllUsers();
    UserResponseDto myUpdateUser(Long myUserId, UserRequestDto myUserRequestDto);
    void myDeleteUser(Long myUserId);
    UserResponseDto myGetUserByEmail(String myUserEmail);
    boolean myCheckEmailExists(String myUserEmail);
}