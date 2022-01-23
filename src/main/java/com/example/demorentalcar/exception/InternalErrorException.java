package com.example.demorentalcar.exception;

public class InternalErrorException extends RuntimeException{

    public InternalErrorException(String message) {
        super(message);
    }
}
