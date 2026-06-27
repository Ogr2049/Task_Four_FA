package com.example.controller;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(UserController.class);
    
    private final UserService myUserService;
    
    @Autowired
    public UserController(UserService myUserService) {
        this.myUserService = myUserService;
    }
    
    @PostMapping
    public ResponseEntity<UserResponseDto> myCreateUser(@Valid @RequestBody UserRequestDto myUserRequestDto) {
        myLoggerInstance.info("REST request to create user: {}", myUserRequestDto.getUserEmail());
        UserResponseDto myCreatedUser = myUserService.myCreateUser(myUserRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(myCreatedUser);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> myGetUserById(@PathVariable Long id) {
        myLoggerInstance.info("REST request to get user by ID: {}", id);
        UserResponseDto myUser = myUserService.myGetUserById(id);
        return ResponseEntity.ok(myUser);
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> myGetAllUsers() {
        myLoggerInstance.info("REST request to get all users");
        List<UserResponseDto> myUsers = myUserService.myGetAllUsers();
        return ResponseEntity.ok(myUsers);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> myUpdateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto myUserRequestDto) {
        myLoggerInstance.info("REST request to update user with ID: {}", id);
        UserResponseDto myUpdatedUser = myUserService.myUpdateUser(id, myUserRequestDto);
        return ResponseEntity.ok(myUpdatedUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> myDeleteUser(@PathVariable Long id) {
        myLoggerInstance.info("REST request to delete user with ID: {}", id);
        myUserService.myDeleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> myGetUserByEmail(@PathVariable String email) {
        myLoggerInstance.info("REST request to get user by email: {}", email);
        UserResponseDto myUser = myUserService.myGetUserByEmail(email);
        return ResponseEntity.ok(myUser);
    }
    
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> myCheckEmailExists(@PathVariable String email) {
        myLoggerInstance.info("REST request to check if email exists: {}", email);
        boolean myExists = myUserService.myCheckEmailExists(email);
        return ResponseEntity.ok(myExists);
    }
}