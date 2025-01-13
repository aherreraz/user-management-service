package com.aherreraz.usermanagement.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class SignUpRequestDto {
    private String name;
    @Pattern(
            regexp = "^\\w+@[a-zA-Z\\d]+\\.[a-zA-Z\\d]+$",
            message = "Invalid email format"
    )
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Z])(?!.*[A-Z].*[A-Z])(?=.*\\d.*\\d)(?!.*\\d.*\\d.*\\d)[a-zA-Z\\d]{8,12}$",
            message = "Password must have 1 uppercase letter, 2 digits, and be 8-12 characters long"
    )
    private String password;
    private List<PhoneDto> phones;
}