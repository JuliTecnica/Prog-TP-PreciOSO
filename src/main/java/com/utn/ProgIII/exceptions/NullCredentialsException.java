package com.utn.ProgIII.exceptions;

public class NullCredentialsException extends BadRequestException {
    public NullCredentialsException(String message) {
        super(message);
    }
}
