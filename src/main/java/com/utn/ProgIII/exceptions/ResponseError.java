package com.utn.ProgIII.exceptions;

import lombok.Getter;

@Getter
public class ResponseError extends RuntimeException {
    public ResponseError(String message) {
        super(message);
    }
}
