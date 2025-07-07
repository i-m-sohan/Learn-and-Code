package com.intimetec.newsportal.exception;

public class NewsPortalException extends RuntimeException {
    public NewsPortalException(String message) {
        super(message);
    }

    public NewsPortalException(String message, Throwable cause) {
        super(message, cause);
    }
}
