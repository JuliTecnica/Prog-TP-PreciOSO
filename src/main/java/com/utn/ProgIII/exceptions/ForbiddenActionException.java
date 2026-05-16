package com.utn.ProgIII.exceptions;

public class ForbiddenActionException extends ResponseError {
    public ForbiddenActionException(String message) {
        super(message);
    }
}
