package com.aherreraz.usermanagement.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private Boolean isActive;
}