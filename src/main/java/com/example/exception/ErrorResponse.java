package com.example.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    
    public ErrorResponse() {}
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime myTimestamp) { this.timestamp = myTimestamp; }
    
    public int getStatus() { return status; }
    public void setStatus(int myStatus) { this.status = myStatus; }
    
    public String getError() { return error; }
    public void setError(String myError) { this.error = myError; }
    
    public String getMessage() { return message; }
    public void setMessage(String myMessage) { this.message = myMessage; }
    
    public String getPath() { return path; }
    public void setPath(String myPath) { this.path = myPath; }
}

class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors;
    
    public ValidationErrorResponse() {}
    
    public Map<String, String> getErrors() { return errors; }
    public void setErrors(Map<String, String> myErrors) { this.errors = myErrors; }
}