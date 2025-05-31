package com.bootcamp.ws.domain.spi;

import com.bootcamp.ws.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserServicePort {
    Mono<User> save(User user);
}
