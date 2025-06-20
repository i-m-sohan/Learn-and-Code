package com.intimetec.newsportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleBadCredentialException(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(Exception illegalArgumentException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", illegalArgumentException.getMessage()));
    }

}
