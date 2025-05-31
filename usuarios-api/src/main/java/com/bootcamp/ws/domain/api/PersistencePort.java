package com.bootcamp.ws.domain.api;

import com.bootcamp.ws.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersistencePort {
    Mono<User> save(User user);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByName(String name);

    Flux<User> getAllUsers();
}
