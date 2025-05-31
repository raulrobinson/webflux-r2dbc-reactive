package com.bootcamp.ws.infrastructure.adapters.persistence;

import com.bootcamp.ws.domain.api.PersistencePort;
import com.bootcamp.ws.domain.model.User;
import com.bootcamp.ws.infrastructure.adapters.persistence.mapper.UserEntityMapper;
import com.bootcamp.ws.infrastructure.adapters.persistence.repository.R2dbcUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersistenceAdapter implements PersistencePort {

    private final R2dbcUserRepository r2dbcUserRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Mono<User> save(User user) {
        return r2dbcUserRepository.save(userEntityMapper.toEntity(user))
                .map(userEntityMapper::toDomain)
                .doOnError(throwable -> {
                    // Log the error or handle it as needed
                    System.err.println("Error saving user: " + throwable.getMessage());
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Error saving user"))); // Handle case where user is null
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return r2dbcUserRepository.existsByEmail(email)
                .doOnError(throwable -> {
                    // Log the error or handle it as needed
                    System.err.println("Error checking existence by email: " + throwable.getMessage());
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Error checking existence by email"))); // Handle case where email is null
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return r2dbcUserRepository.existsByName(name)
                .doOnError(throwable -> {
                    // Log the error or handle it as needed
                    System.err.println("Error checking existence by name: " + throwable.getMessage());
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Error checking existence by name"))); // Handle case where name is null
    }
}
