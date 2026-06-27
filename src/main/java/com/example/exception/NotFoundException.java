package com.example.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String myMessage) {
        super(myMessage);
    }
    
    public NotFoundException(String myMessage, Throwable myCause) {
        super(myMessage, myCause);
    }
}