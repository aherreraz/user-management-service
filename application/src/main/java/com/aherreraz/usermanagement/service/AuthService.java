package com.aherreraz.usermanagement.service;

import com.aherreraz.usermanagement.dto.SignUpRequestDto;
import com.aherreraz.usermanagement.dto.SignUpResponseDto;
import com.aherreraz.usermanagement.exception.DuplicateUserException;
import com.aherreraz.usermanagement.mapper.SignUpMapper;
import com.aherreraz.usermanagement.model.User;
import com.aherreraz.usermanagement.port.PasswordEncoderPort;
import com.aherreraz.usermanagement.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SignUpMapper signUpMapper;
    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public SignUpResponseDto signUp(SignUpRequestDto request) {
        if (userRepository.exists(request.getEmail())) {
            throw new DuplicateUserException("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = signUpMapper.toDomain(request);
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        return signUpMapper.toDto(savedUser);
    }
}