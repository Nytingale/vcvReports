package com.vcv.backend.exceptions;

public class VehicleServiceException extends Exception {
    public VehicleServiceException(String reason) {
        super(reason);
    }
}
