package com.aherreraz.usermanagement.rest.controller;

import com.aherreraz.usermanagement.dto.SignUpRequestDto;
import com.aherreraz.usermanagement.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpRequestDto> signUp(@Valid @RequestBody SignUpRequestDto request) {
        return ResponseEntity.ok(request);
    }
}
