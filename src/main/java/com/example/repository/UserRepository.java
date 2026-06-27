package com.example.repository;

import com.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByUserEmail(String myUserEmail);
    
    boolean existsByUserEmail(String myUserEmail);
    
    @Query("SELECT myUser FROM UserEntity myUser WHERE myUser.userId = :myUserId")
    Optional<UserEntity> findUserById(@Param("myUserId") Long myUserId);
    
    @Query("SELECT myUser FROM UserEntity myUser WHERE myUser.userEmail = :myUserEmail")
    Optional<UserEntity> findUserByEmailAddress(@Param("myUserEmail") String myUserEmail);
}