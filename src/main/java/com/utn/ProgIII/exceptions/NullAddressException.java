package com.utn.ProgIII.exceptions;

public class NullAddressException extends BadRequestException {
    public NullAddressException(String message) {
        super(message);
    }
}
