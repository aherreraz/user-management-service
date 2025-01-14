package com.aherreraz.usermanagement.adapter;

import com.aherreraz.usermanagement.adapter.mapper.UserDboMapper;
import com.aherreraz.usermanagement.adapter.repository.JpaUserRepository;
import com.aherreraz.usermanagement.model.User;
import com.aherreraz.usermanagement.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final JpaUserRepository jpaUserRepository;
    private final UserDboMapper userDboMapper;

    @Override
    public User save(User user) {
        var userToSave = userDboMapper.toDbo(user);
        var userSaved = jpaUserRepository.save(userToSave);
        return userDboMapper.toDomain(userSaved);
    }

    @Override
    public Boolean exists(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var userFound = jpaUserRepository.findByEmail(email);
        return userFound.map(userDboMapper::toDomain);
    }
}