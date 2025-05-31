package com.bootcamp.ws.infrastructure.common.handler;

import com.bootcamp.ws.domain.exceptions.BusinessException;
import com.bootcamp.ws.domain.exceptions.DuplicateResourceException;
import com.bootcamp.ws.domain.exceptions.TechnicalMessage;
import com.bootcamp.ws.infrastructure.common.APIResponse;
import com.bootcamp.ws.infrastructure.common.dto.ErrorDTO;
import com.bootcamp.ws.infrastructure.common.exceptions.NoContentException;
import com.bootcamp.ws.infrastructure.common.exceptions.ProcessorException;
import com.bootcamp.ws.infrastructure.common.exceptions.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
@Order(-2)
public class GlobalErrorHandler {

    public Mono<ServerResponse> handle(Throwable throwable) {
        log.error("Exception captured globally: {}", throwable.toString());

        return switch (throwable) {
            case BusinessException ex -> buildErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    TechnicalMessage.BAD_REQUEST,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            ex.getParameter()
                    ))
            );

            case DuplicateResourceException ex -> buildErrorResponse(
                    HttpStatus.CONFLICT,
                    TechnicalMessage.ALREADY_EXISTS,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            ex.getParameter()
                    ))
            );

            case NoContentException ignored -> ServerResponse.noContent().build();

            case ProcessorException ex -> buildErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    TechnicalMessage.INTERNAL_SERVER_ERROR,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            TechnicalMessage.INTERNAL_SERVER_ERROR.getParameter()
                    ))
            );

            case TechnicalException ex -> buildErrorResponse(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    TechnicalMessage.INTERNAL_ERROR_IN_ADAPTERS,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            TechnicalMessage.INTERNAL_ERROR_IN_ADAPTERS.getParameter()
                    ))
            );

            default -> buildErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    TechnicalMessage.INTERNAL_SERVER_ERROR,
                    List.of(ErrorDTO.of(
                            throwable.getMessage(),
                            TechnicalMessage.INTERNAL_SERVER_ERROR.getParameter()
                    ))
            );
        };
    }

    private Mono<ServerResponse> buildErrorResponse(HttpStatus httpStatus,
                                                    TechnicalMessage technicalMessage,
                                                    List<ErrorDTO> errors) {
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(APIResponse.builder()
                        .code(httpStatus.value())
                        .date(Instant.now().toString())
                        .message(technicalMessage.getMessage())
                        .errors(errors)
                        .build());
    }
}
