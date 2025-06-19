package com.intimetec.newsportal.client;

import com.intimetec.newsportal.dto.ArticleDTO;

import java.util.List;

public interface NewsClient {
    public void setBaseUrl(String url);
    public void setApiKey(String apiKey);

    public List<ArticleDTO> getArticlesPeriodically();
}
