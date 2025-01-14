package com.aherreraz.usermanagement.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorMessage {
    private final LocalDateTime timestamp;
    private final Integer code;
    private final String detail;

    public ErrorMessage(int code, String detail) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.detail = detail;
    }
}