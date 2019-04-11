package com.vcv.backend.exceptions;

public class ControllerException extends Exception {
    public ControllerException(String reason) {
        super(reason);
    }
}
