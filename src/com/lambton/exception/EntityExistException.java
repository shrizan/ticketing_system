package com.lambton.exception;

public class EntityExistException extends RuntimeException {
    private static final String DEFAULT_MSG = "Entity already exist!!!";

    public EntityExistException() {
        super(DEFAULT_MSG);
    }

    public EntityExistException(String message) {
        super(message);
    }
}
