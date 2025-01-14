package com.aherreraz.usermanagement.rest.advice;

import com.aherreraz.usermanagement.exception.DuplicateUserException;
import com.aherreraz.usermanagement.exception.InvalidTokenException;
import com.aherreraz.usermanagement.exception.UserNotFoundException;
import com.aherreraz.usermanagement.model.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, List<ErrorMessage>>> handleDuplicateUserException(DuplicateUserException ex) {
        return ResponseEntity.status(409) // 409 - Conflict
                .body(Map.of("error", List.of(new ErrorMessage(409, ex.getMessage()))));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, List<ErrorMessage>>> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity.status(401) // 401 - Unauthorized
                .body(Map.of("error", List.of(new ErrorMessage(401, ex.getMessage()))));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, List<ErrorMessage>>> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(404) // 404 - Not Found
                .body(Map.of("error", List.of(new ErrorMessage(404, ex.getMessage()))));
    }
}