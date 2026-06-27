package com.example.dto;

import java.time.LocalDateTime;

public class UserResponseDto {
    private Long userId;
    private String userName;
    private String userEmail;
    private Integer userAge;
    private LocalDateTime userCreatedAt;
    
    public UserResponseDto() {}
    
    public UserResponseDto(Long myUserId, String myUserName, String myUserEmail, 
                          Integer myUserAge, LocalDateTime myUserCreatedAt) {
        this.userId = myUserId;
        this.userName = myUserName;
        this.userEmail = myUserEmail;
        this.userAge = myUserAge;
        this.userCreatedAt = myUserCreatedAt;
    }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long myUserId) { this.userId = myUserId; }
    
    public String getUserName() { return userName; }
    public void setUserName(String myUserName) { this.userName = myUserName; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String myUserEmail) { this.userEmail = myUserEmail; }
    
    public Integer getUserAge() { return userAge; }
    public void setUserAge(Integer myUserAge) { this.userAge = myUserAge; }
    
    public LocalDateTime getUserCreatedAt() { return userCreatedAt; }
    public void setUserCreatedAt(LocalDateTime myUserCreatedAt) { this.userCreatedAt = myUserCreatedAt; }
}