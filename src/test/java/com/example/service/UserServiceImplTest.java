package com.example.service;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.entity.UserEntity;
import com.example.exception.NotFoundException;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    
    @Mock
    private UserRepository myUserRepository;
    
    @InjectMocks
    private UserServiceImpl myUserServiceImpl;
    
    private UserEntity myUserEntity;
    private UserRequestDto myUserRequestDto;
    
    @BeforeEach
    void mySetUp() {
        myUserEntity = new UserEntity();
        myUserEntity.setUserId(1L);
        myUserEntity.setUserName("Иван Иванов");
        myUserEntity.setUserEmail("ivan@example.com");
        myUserEntity.setUserAge(30);
        myUserEntity.setUserCreatedAt(LocalDateTime.now());
        
        myUserRequestDto = new UserRequestDto();
        myUserRequestDto.setUserName("Иван Иванов");
        myUserRequestDto.setUserEmail("ivan@example.com");
        myUserRequestDto.setUserAge(30);
    }
    
    @Test
    void myTestCreateUserSuccess() {
        when(myUserRepository.existsByUserEmail("ivan@example.com")).thenReturn(false);
        when(myUserRepository.save(any(UserEntity.class))).thenReturn(myUserEntity);
        
        UserResponseDto myResult = myUserServiceImpl.myCreateUser(myUserRequestDto);
        
        assertThat(myResult).isNotNull();
        assertThat(myResult.getUserId()).isEqualTo(1L);
        assertThat(myResult.getUserName()).isEqualTo("Иван Иванов");
        
        verify(myUserRepository, times(1)).existsByUserEmail("ivan@example.com");
        verify(myUserRepository, times(1)).save(any(UserEntity.class));
    }
    
    @Test
    void myTestCreateUserEmailExists() {
        when(myUserRepository.existsByUserEmail("ivan@example.com")).thenReturn(true);
        
        assertThatThrownBy(() -> myUserServiceImpl.myCreateUser(myUserRequestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email уже зарегистрирован: ivan@example.com");
        
        verify(myUserRepository, times(1)).existsByUserEmail("ivan@example.com");
        verify(myUserRepository, never()).save(any(UserEntity.class));
    }
    
    @Test
    void myTestGetUserByIdSuccess() {
        when(myUserRepository.findById(1L)).thenReturn(Optional.of(myUserEntity));
        
        UserResponseDto myResult = myUserServiceImpl.myGetUserById(1L);
        
        assertThat(myResult).isNotNull();
        assertThat(myResult.getUserId()).isEqualTo(1L);
        
        verify(myUserRepository, times(1)).findById(1L);
    }
    
    @Test
    void myTestGetUserByIdNotFound() {
        when(myUserRepository.findById(999L)).thenReturn(Optional.empty());
        
        assertThatThrownBy(() -> myUserServiceImpl.myGetUserById(999L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Пользователь не найден с ID: 999");
        
        verify(myUserRepository, times(1)).findById(999L);
    }
    
    @Test
    void myTestGetAllUsersSuccess() {
        UserEntity mySecondUserEntity = new UserEntity();
        mySecondUserEntity.setUserId(2L);
        mySecondUserEntity.setUserName("Петр Петров");
        mySecondUserEntity.setUserEmail("petr@example.com");
        mySecondUserEntity.setUserAge(25);
        
        List<UserEntity> myUsers = Arrays.asList(myUserEntity, mySecondUserEntity);
        when(myUserRepository.findAll()).