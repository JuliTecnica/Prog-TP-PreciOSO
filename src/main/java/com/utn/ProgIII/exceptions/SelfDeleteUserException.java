package com.utn.ProgIII.exceptions;

public class SelfDeleteUserException extends ConflictException {
    public SelfDeleteUserException(String message) {
        super(message);
    }
}
