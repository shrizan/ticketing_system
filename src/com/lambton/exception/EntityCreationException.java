package com.lambton.exception;

public class EntityCreationException extends RuntimeException {
    private final static String DEFAULT_MSG = "Failed to create a new entity!!!";

    public EntityCreationException() {
        super(DEFAULT_MSG);
    }

    public EntityCreationException(String message) {
        super(message);
    }
}
