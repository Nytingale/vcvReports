package com.vcv.backend.exceptions;

public class PolicyServiceException extends Exception {
    public PolicyServiceException(String reason) {
        super(reason);
    }
}
