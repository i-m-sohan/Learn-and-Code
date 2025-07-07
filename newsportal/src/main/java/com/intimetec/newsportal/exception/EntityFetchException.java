package com.intimetec.newsportal.exception;

public class EntityFetchException extends NewsPortalException {
    public EntityFetchException(String message) {
        super(message);
    }

    public EntityFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
