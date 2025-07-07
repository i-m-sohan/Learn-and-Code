package com.intimetec.newsportal.console.exception;

public class ArticleSaveException extends RuntimeException {
    public ArticleSaveException(String message) {
        super(message);
    }

    public ArticleSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
