package com.umb.taller.domain.exception;

public abstract class AppException extends RuntimeException {

    protected AppException(String message) {
        super(message);
        System.err.println("AppException: " + message);
    }
}