package com.bootcamp.ws.infrastructure.common.exceptions;

import com.bootcamp.ws.domain.exceptions.TechnicalMessage;

public class NoContentException extends TechnicalException {
    public NoContentException(TechnicalMessage message) {
        super(message);
    }
}
