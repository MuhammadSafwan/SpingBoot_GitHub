package com.recruitment.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Safwan
 */


@ControllerAdvice
public class UserNotFoundExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserNotFoundExceptionResponse> handleException(Exception e) {
        UserNotFoundExceptionResponse exception = new UserNotFoundExceptionResponse(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), "No User with specified username was found");
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

}
