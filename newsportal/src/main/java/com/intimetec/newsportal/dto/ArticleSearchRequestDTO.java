package com.intimetec.newsportal.dto;

public class ArticleSearchRequestDTO {

    private String keyword;

    public ArticleSearchRequestDTO() {}

    public ArticleSearchRequestDTO(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
