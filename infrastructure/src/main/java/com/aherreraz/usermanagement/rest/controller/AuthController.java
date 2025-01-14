package com.aherreraz.usermanagement.rest.controller;

import com.aherreraz.usermanagement.dto.SignUpRequestDto;
import com.aherreraz.usermanagement.dto.SignUpResponseDto;
import com.aherreraz.usermanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto newUser) {
        return ResponseEntity.ok(authService.signUp(newUser));
    }
}