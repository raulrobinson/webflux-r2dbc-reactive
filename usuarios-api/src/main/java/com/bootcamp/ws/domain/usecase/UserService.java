package com.bootcamp.ws.domain.usecase;

import com.bootcamp.ws.domain.api.PersistencePort;
import com.bootcamp.ws.domain.exceptions.DuplicateResourceException;
import com.bootcamp.ws.domain.exceptions.TechnicalMessage;
import com.bootcamp.ws.domain.model.User;
import com.bootcamp.ws.domain.spi.UserServicePort;
import reactor.core.publisher.Mono;

public class UserService implements UserServicePort {

    private final PersistencePort persistencePort;

    public UserService(PersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public Mono<User> save(User user) {
        // 1. Check if a user with the same name already exists
        return persistencePort.existsByName(user.getName())
                .flatMap(existsByName -> {
                    if (existsByName) {
                        return Mono.error(new DuplicateResourceException(
                                TechnicalMessage.ALREADY_EXISTS,
                                "Already exists a user with the same name",
                                String.format("Name: %s", user.getName())
                        ));
                    }

                    // 2. If not, check if a user with the same email already exists
                    return persistencePort.existsByEmail(user.getEmail())
                            .flatMap(existsByEmail -> {
                                if (existsByEmail) {
                                    return Mono.error(new DuplicateResourceException(
                                            TechnicalMessage.ALREADY_EXISTS,
                                            "Already exists a user with the same email",
                                            String.format("Email: %s", user.getEmail())
                                    ));
                                }

                                // 3. If neither exists, save the user
                                return persistencePort.save(user);
                            });
                });
    }
}
