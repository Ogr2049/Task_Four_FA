package com.example.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger myLoggerInstance = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> myHandleNotFoundException(NotFoundException myException) {
        myLoggerInstance.error("Not found: {}", myException.getMessage());
        
        ErrorResponse myErrorResponse = new ErrorResponse();
        myErrorResponse.setTimestamp(LocalDateTime.now());
        myErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        myErrorResponse.setError("Not Found");
        myErrorResponse.setMessage(myException.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(myErrorResponse);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> myHandleIllegalArgumentException(IllegalArgumentException myException) {
        myLoggerInstance.error("Illegal argument: {}", myException.getMessage());
        
        ErrorResponse myErrorResponse = new ErrorResponse();
        myErrorResponse.setTimestamp(LocalDateTime.now());
        myErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        myErrorResponse.setError("Bad Request");
        myErrorResponse.setMessage(myException.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myErrorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> myHandleValidationExceptions(
            MethodArgumentNotValidException myException) {
        myLoggerInstance.error("Validation error: {}", myException.getMessage());
        
        Map<String, String> myErrors = new HashMap<>();
        for (FieldError myFieldError : myException.getBindingResult().getFieldErrors()) {
            myErrors.put(myFieldError.getField(), myFieldError.getDefaultMessage());
        }
        
        ValidationErrorResponse myValidationErrorResponse = new ValidationErrorResponse();
        myValidationErrorResponse.setTimestamp(LocalDateTime.now());
        myValidationErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        myValidationErrorResponse.setError("Validation Failed");
        myValidationErrorResponse.setMessage("Ошибка валидации запроса");
        myValidationErrorResponse.setErrors(myErrors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myValidationErrorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> myHandleGeneralException(Exception myException) {
        myLoggerInstance.error("Internal server error: {}", myException.getMessage(), myException);
        
        ErrorResponse myErrorResponse = new ErrorResponse();
        myErrorResponse.setTimestamp(LocalDateTime.now());
        myErrorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        myErrorResponse.setError("Internal Server Error");
        myErrorResponse.setMessage("Произошла внутренняя ошибка сервера");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(myErrorResponse);
    }
}