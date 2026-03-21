package com.utn.ProgIII.exceptions;

import lombok.Getter;

@Getter
public class ResponseError extends RuntimeException {
    String title;

    public ResponseError(String message) {
        super(message);
    }
}
