package com.intimetec.newsportal.console.exception;

public class ArticleExcpetion extends RuntimeException {
    public ArticleExcpetion(String message) {
        super(message);
    }

    public ArticleExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
}
