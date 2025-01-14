package com.aherreraz.usermanagement.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class ErrorMessage {
    private final LocalDateTime timestamp;
    @Setter
    private Integer code;
    @Setter
    private String detail;

    public ErrorMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorMessage(int code, String detail) {
        this();
        this.code = code;
        this.detail = detail;
    }
}