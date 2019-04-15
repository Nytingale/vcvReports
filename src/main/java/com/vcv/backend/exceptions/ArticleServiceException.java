package com.vcv.backend.exceptions;

public class ArticleServiceException extends Exception {
    public ArticleServiceException(String reason) {
        super(reason);
    }
}
