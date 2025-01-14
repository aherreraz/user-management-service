package com.aherreraz.usermanagement.mapper;

import com.aherreraz.usermanagement.dto.LoginResponseDto;
import com.aherreraz.usermanagement.model.User;
import com.aherreraz.usermanagement.util.JwtUtil;
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
public interface LoginMapper {
    @Mapping(target = "token", source = "email", qualifiedByName = "generateToken")
    LoginResponseDto toDto(User user, @Context JwtUtil jwtUtil);

    @Named("generateToken")
    static String generateToken(String email, @Context JwtUtil jwtUtil) {
        return jwtUtil.generateToken(email);
    }
}