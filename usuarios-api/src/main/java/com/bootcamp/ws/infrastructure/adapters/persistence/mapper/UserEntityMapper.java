package com.bootcamp.ws.infrastructure.adapters.persistence.mapper;

import com.bootcamp.ws.infrastructure.adapters.persistence.entity.UserEntity;
import com.bootcamp.ws.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        if (user == null) return null;
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.isStatus())
                .build();
    }

    public User toDomain(UserEntity userEntity) {
        if (userEntity == null) return null;
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .status(userEntity.getStatus())
                .build();
    }

    public User toListOfUsers(UserEntity entity) {
        if (entity == null) return null;
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .status(Boolean.TRUE.equals(entity.getStatus()))
                .build();
    }

}
