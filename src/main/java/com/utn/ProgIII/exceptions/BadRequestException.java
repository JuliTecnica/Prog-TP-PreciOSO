package com.utn.ProgIII.exceptions;

public class BadRequestException extends ResponseError {
    public BadRequestException(String message) {
        super(message);
    }
}
