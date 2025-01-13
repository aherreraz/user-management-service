package com.aherreraz.usermanagement.dto;

import lombok.Data;

@Data
public class PhoneDto {
    private Long number;
    private Integer cityCode;
    private String countryCode;
}