package com.intimetec.newsportal.exception;

public class ArticleCategoryNotFoundException extends RuntimeException {
    public ArticleCategoryNotFoundException(String message) {
        super(message);
    }
}
