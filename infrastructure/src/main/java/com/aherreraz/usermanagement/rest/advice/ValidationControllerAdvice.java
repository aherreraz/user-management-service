package com.aherreraz.usermanagement.rest.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> Map.of("field", fieldError.getField(), "message", fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return  ResponseEntity.badRequest()
                .body(Map.of("errors", errors));
    }
}
