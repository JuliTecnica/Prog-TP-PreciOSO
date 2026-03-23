package com.utn.ProgIII.exceptions;

public class InvalidProductStatusException extends BadRequestException {

    public InvalidProductStatusException(String message) {
        super(message);
    }
}
