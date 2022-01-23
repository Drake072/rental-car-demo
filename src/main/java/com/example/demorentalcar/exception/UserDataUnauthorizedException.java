package com.example.demorentalcar.exception;

public class UserDataUnauthorizedException extends UserApiException {

    public UserDataUnauthorizedException(String message) {
        super(message);
    }

}
