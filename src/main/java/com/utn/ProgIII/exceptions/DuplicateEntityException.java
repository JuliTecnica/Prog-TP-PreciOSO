package com.utn.ProgIII.exceptions;

public class DuplicateEntityException extends ConflictException {
    public DuplicateEntityException(String msg) {
        super(msg);
    }
}
