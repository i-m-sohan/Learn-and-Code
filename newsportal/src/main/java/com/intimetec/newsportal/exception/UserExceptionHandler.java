package com.intimetec.newsportal.exception;

import com.intimetec.newsportal.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException userAlreadyExistException){
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<?> handleInvalidCredentialExceptions(RuntimeException userNotFoundException){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
