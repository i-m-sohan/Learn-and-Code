package com.intimetec.newsportal.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ArticleDTO;

import java.util.List;

public class NewsApiOrgClientImpl implements NewsClient {

    public String baseUrl;
    public String apiKey;
    private ObjectMapper objectMapper;

    public NewsApiOrgClientImpl(){
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<ArticleDTO> getArticlesPeriodically() {
        return List.of();
    }

    @Override
    public void setBaseUrl(String url) {

    }

    @Override
    public void setApiKey(String apiKey) {

    }
}
