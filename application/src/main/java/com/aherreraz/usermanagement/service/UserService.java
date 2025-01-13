package com.aherreraz.usermanagement.service;

import com.aherreraz.usermanagement.dto.SignUpRequestDto;
import com.aherreraz.usermanagement.dto.SignUpResponseDto;
import com.aherreraz.usermanagement.mapper.SignUpMapper;
import com.aherreraz.usermanagement.model.User;
import com.aherreraz.usermanagement.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SignUpMapper signUpMapper;
    private final UserRepositoryPort userRepository;

    public SignUpResponseDto signUp(SignUpRequestDto request) {
        User user = signUpMapper.toDomain(request);
        User savedUser = userRepository.create(user);
        return signUpMapper.toDto(savedUser);
    }
}