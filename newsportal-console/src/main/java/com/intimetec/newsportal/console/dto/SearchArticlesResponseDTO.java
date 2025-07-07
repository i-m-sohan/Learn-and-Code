package com.intimetec.newsportal.console.dto;

import java.util.List;

public class SearchArticlesResponseDTO {

    private List<ArticleDTO> Articles;

    public SearchArticlesResponseDTO() {}

    public SearchArticlesResponseDTO(List<ArticleDTO> articles) {
        this.Articles = articles;
    }

    public List<ArticleDTO> getArticles() {
        return Articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.Articles = articles;
    }
}
