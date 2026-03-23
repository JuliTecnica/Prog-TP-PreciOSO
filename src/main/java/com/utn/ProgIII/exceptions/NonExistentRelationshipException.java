package com.utn.ProgIII.exceptions;

public class NonExistentRelationshipException extends NotFoundException {
    public NonExistentRelationshipException(String msg) {
        super(msg);
    }
}
