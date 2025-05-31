package com.bootcamp.ws.infrastructure.common.exceptions;

import com.bootcamp.ws.domain.exceptions.TechnicalMessage;

public class ProcessorException extends TechnicalException {
    public ProcessorException(TechnicalMessage message) {
        super(message);
    }

    public ProcessorException(TechnicalMessage message, Throwable cause) {
        super(message, cause);
    }
}
