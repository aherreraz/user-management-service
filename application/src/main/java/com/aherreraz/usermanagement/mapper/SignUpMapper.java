package com.aherreraz.usermanagement.mapper;

import com.aherreraz.usermanagement.dto.SignUpRequestDto;
import com.aherreraz.usermanagement.dto.SignUpResponseDto;
import com.aherreraz.usermanagement.model.User;
import com.aherreraz.usermanagement.port.PasswordEncoderPort;
import com.aherreraz.usermanagement.util.JwtUtil;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SignUpMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    User toDomain(SignUpRequestDto dto, @Context PasswordEncoderPort passwordEncoder);

    @Mapping(target = "token", source = "email", qualifiedByName = "generateToken")
    @BeanMapping(ignoreUnmappedSourceProperties = {"name", "password", "phones"})
    SignUpResponseDto toDto(User user, @Context JwtUtil jwtUtil);

    @Named("encodePassword")
    static String encodePassword(String rawPassword, @Context PasswordEncoderPort passwordEncoder) {
        return passwordEncoder.encode(rawPassword);
    }

    @Named("generateToken")
    static String generateToken(String email, @Context JwtUtil jwtUtil) {
        return jwtUtil.generateToken(email);
    }
}