package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "app_users")
@NamedQueries({
    @NamedQuery(name = "UserEntity.findAllUsers", query = "SELECT myUser FROM UserEntity myUser"),
    @NamedQuery(name = "UserEntity.findUserByEmail", query = "SELECT myUser FROM UserEntity myUser WHERE myUser.userEmail = :userEmail")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_generator")
    @SequenceGenerator(name = "user_sequence_generator", sequenceName = "user_sequence_table", allocationSize = 1)
    private Long userId;
    
    @Column(name = "user_full_name", nullable = false, length = 50)
    private String userName;
    
    @Column(name = "user_email_address", unique = true, nullable = false, length = 100)
    private String userEmail;
    
    @Column(name = "user_age_value", nullable = false)
    private Integer userAge;
    
    @Column(name = "user_registration_timestamp", nullable = false)
    private LocalDateTime userCreatedAt;
    
    @Version
    private Long versionNumber;
    
    @PrePersist
    protected void executeBeforePersist() {
        userCreatedAt = LocalDateTime.now();
    }
    
    public UserEntity() {}
    
    public UserEntity(String myUserName, String myUserEmail, Integer myUserAge) {
        this.userName = myUserName;
        this.userEmail = myUserEmail;
        this.userAge = myUserAge;
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
    
    public Long getVersionNumber() { return versionNumber; }
    
    @Override
    public boolean equals(Object myObject) {
        if (this == myObject) return true;
        if (!(myObject instanceof UserEntity)) return false;
        UserEntity thatUser = (UserEntity) myObject;
        return Objects.equals(userId, thatUser.userId) && 
               Objects.equals(userEmail, thatUser.userEmail);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userId, userEmail);
    }
    
    @Override
    public String toString() {
        return String.format("UserEntity[userId=%d, userName='%s', userEmail='%s', userAge=%d, userCreatedAt=%s]", 
                           userId, userName, userEmail, userAge, userCreatedAt);
    }
}