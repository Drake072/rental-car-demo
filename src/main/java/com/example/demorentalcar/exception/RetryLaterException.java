package com.example.demorentalcar.exception;

public class RetryLaterException extends RuntimeException{
    public RetryLaterException(String message) {
        super(message);
    }
}
