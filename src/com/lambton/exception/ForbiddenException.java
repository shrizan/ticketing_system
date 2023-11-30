package com.lambton.exception;

public class ForbiddenException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Action not allowed.";

    public ForbiddenException() {
        super(DEFAULT_MESSAGE);
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
