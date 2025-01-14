package com.aherreraz.usermanagement.port;

public interface PasswordEncoderPort {
    String encode(String rawPassword);
}