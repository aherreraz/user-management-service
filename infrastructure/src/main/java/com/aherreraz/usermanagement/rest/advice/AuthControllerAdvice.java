package com.aherreraz.usermanagement.rest.advice;

import com.aherreraz.usermanagement.exception.DuplicateUserException;
import com.aherreraz.usermanagement.exception.InvalidTokenException;
import com.aherreraz.usermanagement.exception.UserNotFoundException;
import com.aherreraz.usermanagement.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, List<ErrorMessage>>> handleDuplicateUserException(DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", List.of(new ErrorMessage(HttpStatus.CONFLICT.value(), ex.getMessage()))));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, List<ErrorMessage>>> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", List.of(new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()))));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, List<ErrorMessage>>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", List.of(new ErrorMessage(HttpStatus.NOT_FOUND.value(), ex.getMessage()))));
    }
}