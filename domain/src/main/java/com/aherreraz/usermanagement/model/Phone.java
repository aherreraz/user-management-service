package com.aherreraz.usermanagement.model;

import lombok.Data;

@Data
public class Phone {
    private Long number;
    private int cityCode;
    private String countryCode;
}