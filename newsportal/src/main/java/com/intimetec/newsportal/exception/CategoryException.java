package com.intimetec.newsportal.exception;

public class CategoryException extends NewsPortalException {
    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
      super(message, cause);
    }
}
