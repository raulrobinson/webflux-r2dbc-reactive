package com.bootcamp.ws.application.config;

import com.bootcamp.ws.domain.api.PersistencePort;
import com.bootcamp.ws.domain.usecase.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public UserService userService(PersistencePort persistencePort) {
        return new UserService(persistencePort);
    }
}
