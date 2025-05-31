package com.bootcamp.ws.domain.usecase;

import com.bootcamp.ws.domain.api.PersistencePort;
import com.bootcamp.ws.domain.exceptions.BusinessException;
import com.bootcamp.ws.domain.exceptions.DuplicateResourceException;
import com.bootcamp.ws.domain.exceptions.TechnicalMessage;
import com.bootcamp.ws.domain.model.User;
import com.bootcamp.ws.domain.spi.UserServicePort;
import com.bootcamp.ws.infrastructure.common.exceptions.NoContentException;
import reactor.core.publisher.Flux;
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

    @Override
    public Flux<User> getAllUsers() {
        // 1. Retrieve all users from the persistence layer
        return persistencePort.getAllUsers()
                .switchIfEmpty(Flux.error(new NoContentException(
                        TechnicalMessage.NO_CONTENT
                        ))); // Handle case where no users are found
    }

    @Override
    public Mono<Boolean> deleteUser(Long userId) {
        return persistencePort.findById(userId)
                .flatMap(user -> {
                    // 1. Check if the user exists
                    if (user == null) {
                        return Mono.error(new BusinessException(
                                TechnicalMessage.NOT_FOUND.getMessage(),
                                "User not found",
                                String.format("User ID: %s", userId)
                        ));
                    }
                    // 2. If exists, delete the user
                    return persistencePort.deleteUser(userId);
                })
                .then(Mono.just(true));
                //.then(); // Return a Mono<Void> to indicate completion
    }
}
