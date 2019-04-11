package com.vcv.backend.exceptions;

public class CompanyServiceException extends Exception {
    public CompanyServiceException(String reason) {
        super(reason);
    }
}
