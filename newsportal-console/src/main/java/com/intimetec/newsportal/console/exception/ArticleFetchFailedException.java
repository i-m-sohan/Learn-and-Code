package com.intimetec.newsportal.console.exception;

public class ArticleFetchFailedException extends RuntimeException {
    public ArticleFetchFailedException(String message) {
        super(message);
    }
}
