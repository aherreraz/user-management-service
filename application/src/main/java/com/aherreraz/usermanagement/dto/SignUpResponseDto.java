package com.aherreraz.usermanagement.dto;

import com.aherreraz.usermanagement.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private Boolean isActive;
}
