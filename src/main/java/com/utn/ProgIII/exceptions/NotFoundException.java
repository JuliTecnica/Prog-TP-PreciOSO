package com.utn.ProgIII.exceptions;


public class NotFoundException extends ResponseError {
    String title;

    public NotFoundException(String message) {
        super(message);
    }
}
