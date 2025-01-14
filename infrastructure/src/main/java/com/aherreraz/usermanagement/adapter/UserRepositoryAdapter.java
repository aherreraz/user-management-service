package com.aherreraz.usermanagement.adapter;

import com.aherreraz.usermanagement.adapter.mapper.UserDboMapper;
import com.aherreraz.usermanagement.adapter.repository.JpaUserRepository;
import com.aherreraz.usermanagement.exception.UserNotFoundException;
import com.aherreraz.usermanagement.model.User;
import com.aherreraz.usermanagement.port.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public User findByCredentials(String email, String password) {
        var userFound = jpaUserRepository.findByEmailAndPassword(email, password);
        return userFound.map(userDboMapper::toDomain).orElseThrow(() -> new UserNotFoundException("Invalid credentials"));
    }
}
