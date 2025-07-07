package com.intimetec.newsportal.console.dto;

import java.util.List;

public class HeadlineResponseDTO {
    List<ArticleDTO> headlines;

    public List<ArticleDTO> getHeadlines() {
        return headlines;
    }

    public void setHeadlines(List<ArticleDTO> headlines) {
        this.headlines = headlines;
    }
}
