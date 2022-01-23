package com.example.demorentalcar.exception;

import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.demorentalcar.constant.ConstValue.REQUEST_ID;

@RestControllerAdvice
@Log4j2
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionMessage> handlUserNotFound(UserNotFountException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessage> handlUserExist(UserExistException ex) {
        log.info(MDC.get(REQUEST_ID));
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserDataUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionMessage> handlUnauthorizedAccess(UserDataUnauthorizedException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessage> handleInvalidInput(InvalidInputException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CarReservationFailedException.class, CarReturnFailedException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionMessage> handleCarUnavailable(CarReservationFailedException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InternalErrorException.class, RetryLaterException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionMessage> handleInternalError(CarReservationFailedException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
