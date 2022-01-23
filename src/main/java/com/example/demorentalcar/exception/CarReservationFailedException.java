package com.example.demorentalcar.exception;

public class CarReservationFailedException extends RuntimeException{

    public CarReservationFailedException(String message) {
        super(message);
    }

}
