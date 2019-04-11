package com.vcv.backend.exceptions;

public class UserServiceException extends Exception {
    public UserServiceException(String reason) {
        super(reason);
    }
}
