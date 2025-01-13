package com.aherreraz.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private Boolean isActive;

    public User() {
        this.id = UUID.randomUUID();
        this.created = LocalDateTime.now();
        this.lastLogin = null;
        this.isActive = true;
    }
}