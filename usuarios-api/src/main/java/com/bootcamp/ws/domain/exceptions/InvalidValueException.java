package com.bootcamp.ws.domain.exceptions;

import lombok.Getter;

@Getter
public class InvalidValueException extends BusinessException {
    public InvalidValueException(TechnicalMessage parameter, String message) {
        super(message, "INVALID_VALUE", parameter.getMessage());
    }
}
