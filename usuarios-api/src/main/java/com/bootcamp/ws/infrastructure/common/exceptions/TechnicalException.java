package com.bootcamp.ws.infrastructure.common.exceptions;

import com.bootcamp.ws.domain.exceptions.TechnicalMessage;

public class TechnicalException extends RuntimeException {
    public TechnicalException(TechnicalMessage message) {
        super(message.getMessage());
    }

    public TechnicalException(TechnicalMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }
}
