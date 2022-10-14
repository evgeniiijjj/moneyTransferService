package com.example.backend.exception;

public class RejectedOperationException extends RuntimeException {
    public RejectedOperationException(String message) {
        super(message);
    }
}
