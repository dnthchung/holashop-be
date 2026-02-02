package com.chungdt03.holashopbe.exceptions.payload;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
