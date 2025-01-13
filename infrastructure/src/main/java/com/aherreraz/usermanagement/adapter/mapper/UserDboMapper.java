package com.aherreraz.usermanagement.adapter.mapper;

import com.aherreraz.usermanagement.adapter.entity.UserEntity;
import com.aherreraz.usermanagement.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserDboMapper {
    UserEntity toDbo(User user);

    User toDomain(UserEntity userEntity);
}