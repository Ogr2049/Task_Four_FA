package com.example.service;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.entity.UserEntity;
import com.example.exception.NotFoundException;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserRepository myUserRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }
    
    @Override
    @Transactional
    public UserResponseDto myCreateUser(UserRequestDto myUserRequestDto) {
        myLoggerInstance.info("Creating new user with email: {}", myUserRequestDto.getUserEmail());
        
        if (myUserRepository.existsByUserEmail(myUserRequestDto.getUserEmail())) {
            throw new IllegalArgumentException("Email уже зарегистрирован: " + myUserRequestDto.getUserEmail());
        }
        
        UserEntity myUserEntity = new UserEntity(
            myUserRequestDto.getUserName(),
            myUserRequestDto.getUserEmail(),
            myUserRequestDto.getUserAge()
        );
        
        UserEntity mySavedUser = myUserRepository.save(myUserEntity);
        myLoggerInstance.info("User created successfully with ID: {}", mySavedUser.getUserId());
        
        return myConvertToDto(mySavedUser);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto myGetUserById(Long myUserId) {
        myLoggerInstance.info("Fetching user with ID: {}", myUserId);
        
        UserEntity myUserEntity = myUserRepository.findById(myUserId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + myUserId));
        
        return myConvertToDto(myUserEntity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> myGetAllUsers() {
        myLoggerInstance.info("Fetching all users");
        
        List<UserEntity> myUserEntities = myUserRepository.findAll();
        
        return myUserEntities.stream()
                .map(this::myConvertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public UserResponseDto myUpdateUser(Long myUserId, UserRequestDto myUserRequestDto) {
        myLoggerInstance.info("Updating user with ID: {}", myUserId);
        
        UserEntity myExistingUser = myUserRepository.findById(myUserId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + myUserId));
        
        if (!myExistingUser.getUserEmail().equals(myUserRequestDto.getUserEmail()) &&
            myUserRepository.existsByUserEmail(myUserRequestDto.getUserEmail())) {
            throw new IllegalArgumentException("Email уже используется: " + myUserRequestDto.getUserEmail());
        }
        
        myExistingUser.setUserName(myUserRequestDto.getUserName());
        myExistingUser.setUserEmail(myUserRequestDto.getUserEmail());
        myExistingUser.setUserAge(myUserRequestDto.getUserAge());
        
        UserEntity myUpdatedUser = myUserRepository.save(myExistingUser);
        myLoggerInstance.info("User updated successfully with ID: {}", myUserId);
        
        return myConvertToDto(myUpdatedUser);
    }
    
    @Override
    @Transactional
    public void myDeleteUser(Long myUserId) {
        myLoggerInstance.info("Deleting user with ID: {}", myUserId);
        
        if (!myUserRepository.existsById(myUserId)) {
            throw new NotFoundException("Пользователь не найден с ID: " + myUserId);
        }
        
        myUserRepository.deleteById(myUserId);
        myLoggerInstance.info("User deleted successfully with ID: {}", myUserId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserResponseDto myGetUserByEmail(String myUserEmail) {
        myLoggerInstance.info("Fetching user by email: {}", myUserEmail);
        
        UserEntity myUserEntity = myUserRepository.findByUserEmail(myUserEmail)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с email: " + myUserEmail));
        
        return myConvertToDto(myUserEntity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean myCheckEmailExists(String myUserEmail) {
        return myUserRepository.existsByUserEmail(myUserEmail);
    }
    
    private UserResponseDto myConvertToDto(UserEntity myUserEntity) {
        UserResponseDto myUserResponseDto = new UserResponseDto();
        myUserResponseDto.setUserId(myUserEntity.getUserId());
        myUserResponseDto.setUserName(myUserEntity.getUserName());
        myUserResponseDto.setUserEmail(myUserEntity.getUserEmail());
        myUserResponseDto.setUserAge(myUserEntity.getUserAge());
        myUserResponseDto.setUserCreatedAt(myUserEntity.getUserCreatedAt());
        return myUserResponseDto;
    }
}