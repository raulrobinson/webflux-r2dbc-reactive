package com.bootcamp.ws.domain.spi;

import com.bootcamp.ws.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServicePort {
    Mono<User> save(User user);

    Flux<User> getAllUsers();

    Mono<Boolean> deleteUser(Long userId);
}
