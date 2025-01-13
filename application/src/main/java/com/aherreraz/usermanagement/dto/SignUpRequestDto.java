package com.aherreraz.usermanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;
}