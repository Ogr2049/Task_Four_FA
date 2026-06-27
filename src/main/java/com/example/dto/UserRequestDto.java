package com.example.dto;

import jakarta.validation.constraints.*;

public class UserRequestDto {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ\\s-]+$", message = "Имя может содержать только буквы, пробелы и дефисы")
    private String userName;
    
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Неверный формат email")
    @Size(max = 100, message = "Email не может превышать 100 символов")
    private String userEmail;
    
    @NotNull(message = "Возраст не может быть пустым")
    @Min(value = 1, message = "Возраст должен быть не менее 1 года")
    @Max(value = 120, message = "Возраст должен быть не более 120 лет")
    private Integer userAge;
    
    public UserRequestDto() {}
    
    public UserRequestDto(String myUserName, String myUserEmail, Integer myUserAge) {
        this.userName = myUserName;
        this.userEmail = myUserEmail;
        this.userAge = myUserAge;
    }
    
    public String getUserName() { return userName; }
    public void setUserName(String myUserName) { this.userName = myUserName; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String myUserEmail) { this.userEmail = myUserEmail; }
    
    public Integer getUserAge() { return userAge; }
    public void setUserAge(Integer myUserAge) { this.userAge = myUserAge; }
}