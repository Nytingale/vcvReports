package com.vcv.backend.exceptions;

public class FileServiceException extends Exception {
    public FileServiceException(String reason) {
        super(reason);
    }
}
