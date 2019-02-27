package com.vcv.backend.exceptions;

public class VcvInvalidParameterException extends Exception {
    public VcvInvalidParameterException(String reason) {
        super(reason);
    }
}
