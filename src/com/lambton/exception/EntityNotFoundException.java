package com.lambton.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final String DEFAULT_MSG = "Entity not found!!!";

    public EntityNotFoundException() {
        super(DEFAULT_MSG);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
