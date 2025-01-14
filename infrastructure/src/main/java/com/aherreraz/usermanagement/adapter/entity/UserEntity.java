package com.aherreraz.usermanagement.adapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    @ElementCollection
    @CollectionTable(
            name = "phones",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private List<PhoneEntity> phones;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private Boolean isActive;
}