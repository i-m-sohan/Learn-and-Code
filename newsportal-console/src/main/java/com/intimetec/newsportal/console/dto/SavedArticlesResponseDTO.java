package com.intimetec.newsportal.console.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SavedArticlesResponseDTO {
    @JsonProperty("Saved Articles")
    private List<ArticleDTO> savedArticles;

    public List<ArticleDTO> getSavedArticles() {
        return savedArticles;
    }

    public void setSavedArticles(List<ArticleDTO> savedArticles) {
        this.savedArticles = savedArticles;
    }
}
