package com.utn.ProgIII.exceptions;

public class ForbiddenModificationException extends InvalidActionException {
    public ForbiddenModificationException(String message) {
        super(message);
    }
}
