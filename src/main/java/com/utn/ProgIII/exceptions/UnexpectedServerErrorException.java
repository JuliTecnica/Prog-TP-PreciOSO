package com.utn.ProgIII.exceptions;

import lombok.Getter;

@Getter
public class UnexpectedServerErrorException extends ResponseError {
    private int httpcode = -1;

    public UnexpectedServerErrorException(String message) {
        super(message);
    }

    public UnexpectedServerErrorException(String message, int httpcode)
    {
        super(message);
        this.httpcode = httpcode;
    }

}
