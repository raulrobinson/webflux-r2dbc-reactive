package com.bootcamp.ws.infrastructure.inbound.handler;

import com.bootcamp.ws.infrastructure.common.handler.GlobalErrorHandler;
import com.bootcamp.ws.infrastructure.inbound.mapper.UserMapper;
import com.bootcamp.ws.infrastructure.inbound.dto.UserDTO;
import com.bootcamp.ws.domain.spi.UserServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserServicePort userService;
    private final UserMapper mapper;
    private final GlobalErrorHandler errorHandler;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserDTO.class)
                .map(mapper::toDomain)
                .flatMap(userService::save)
                .flatMap(savedUser -> ServerResponse.ok().bodyValue(savedUser))
                .onErrorResume(errorHandler::handle);
//                .onErrorResume(e -> {
//                    log.error("Error creating user: {}", e.getMessage());
//                    return ServerResponse.status(500).bodyValue("Internal Server Error");
//                });
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return userService.getAllUsers()
                .collectList()
                .flatMap(users -> ServerResponse.ok().bodyValue(users))
                .onErrorResume(errorHandler::handle);
    }
}
