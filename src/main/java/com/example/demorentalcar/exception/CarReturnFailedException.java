package com.example.demorentalcar.exception;

public class CarReturnFailedException extends RuntimeException{

    public CarReturnFailedException(String message) {
        super(message);
    }

}
