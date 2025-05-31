package com.bootcamp.ws.infrastructure.inbound.mapper;

import com.bootcamp.ws.infrastructure.inbound.dto.UserDTO;
import com.bootcamp.ws.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}
