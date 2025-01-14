package com.aherreraz.usermanagement.service;

import com.aherreraz.usermanagement.dto.LoginRequestDto;
import com.aherreraz.usermanagement.dto.LoginResponseDto;
import com.aherreraz.usermanagement.dto.SignUpRequestDto;
import com.aherreraz.usermanagement.dto.SignUpResponseDto;
import com.aherreraz.usermanagement.exception.DuplicateUserException;
import com.aherreraz.usermanagement.exception.InvalidTokenException;
import com.aherreraz.usermanagement.exception.UserNotFoundException;
import com.aherreraz.usermanagement.mapper.LoginMapper;
import com.aherreraz.usermanagement.mapper.SignUpMapper;
import com.aherreraz.usermanagement.model.User;
import com.aherreraz.usermanagement.port.PasswordEncoderPort;
import com.aherreraz.usermanagement.port.UserRepositoryPort;
import com.aherreraz.usermanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SignUpMapper signUpMapper;
    private final LoginMapper loginMapper;
    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignUpResponseDto signUp(SignUpRequestDto dto) {
        if (userRepository.exists(dto.getEmail())) {
            throw new DuplicateUserException("User already exists");
        }

        User user = signUpMapper.toDomain(dto, passwordEncoder);
        User savedUser = userRepository.save(user);
        return signUpMapper.toDto(savedUser, jwtUtil);
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        if (!jwtUtil.validateToken(dto.getToken())) {
            throw new InvalidTokenException("Invalid or expired token");
        }

        String email = jwtUtil.getEmailFromToken(dto.getToken());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setLastLogin(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return loginMapper.toDto(savedUser, jwtUtil);
    }
}