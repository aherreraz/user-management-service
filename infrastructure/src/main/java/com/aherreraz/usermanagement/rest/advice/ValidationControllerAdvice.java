package com.aherreraz.usermanagement.rest.advice;

import com.aherreraz.usermanagement.model.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<ErrorMessage>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorMessage(422, fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return ResponseEntity.status(422) // 422 - Unprocessable Content
                .body(Map.of("error", errors));
    }
}