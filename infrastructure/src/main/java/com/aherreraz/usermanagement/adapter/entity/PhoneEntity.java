package com.aherreraz.usermanagement.adapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhoneEntity {
    private Long number;
    private Integer cityCode;
    private String countryCode;
}