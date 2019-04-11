package com.vcv.backend.exceptions;

public class JobServiceException extends Exception {
    public JobServiceException(String reason) {
        super(reason);
    }
}
