package com.aherreraz.usermanagement.adapter.repository;

import com.aherreraz.usermanagement.adapter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}