package com.aherreraz.usermanagement.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Token is required")
    private String token;
}