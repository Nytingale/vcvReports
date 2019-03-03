package com.vcv.backend.exceptions;

public class ClaimServiceException extends Exception {
    public ClaimServiceException(String reason) {
        super(reason);
    }
}
