package com.chungdt03.holashopbe.exceptions.payload;

public class PermissionDenyException extends RuntimeException {
    public PermissionDenyException(String message) {
        super(message);
    }
}
