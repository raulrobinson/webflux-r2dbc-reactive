package com.bootcamp.ws.infrastructure.inbound;

import com.bootcamp.ws.infrastructure.inbound.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    // This class can be used to define routes for the application.
    // For example, you can use Spring WebFlux to create a router function
    // that maps HTTP requests to handler methods in UserHandler.

    // Example:
     @Bean
     public RouterFunction<ServerResponse> route(UserHandler userHandler) {
         return RouterFunctions.route()
//                 .GET("/users", userHandler::getAllUsers)
                 .POST("/users", userHandler::createUser)
                 .build();
     }
}
