package com.utn.ProgIII.exceptions;

public class DuplicateRelationshipException extends ConflictException {
    public DuplicateRelationshipException(String msg) {
        super(msg);
    }
}
