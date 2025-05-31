package com.bootcamp.ws.infrastructure.adapters.persistence.repository;

import com.bootcamp.ws.infrastructure.adapters.persistence.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface R2dbcUserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByName(String name);
}
