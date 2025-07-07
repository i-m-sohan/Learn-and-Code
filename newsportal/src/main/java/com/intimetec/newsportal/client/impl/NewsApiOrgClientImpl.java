package com.intimetec.newsportal.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.NewsApiArticleDTO;
import com.intimetec.newsportal.dto.NewsApiResponseDTO;
import com.intimetec.newsportal.dto.TheNewsApiResponseDTO;
import com.intimetec.newsportal.utils.ApiUtilis;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component("news api")
public class NewsApiOrgClientImpl implements NewsClient {

    public String baseUrl;
    public String apiKey;
    private ObjectMapper objectMapper;

    public NewsApiOrgClientImpl(){
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<ArticleDTO> getArticlesPeriodically() {
        try {
            baseUrl += "/everything";
            Map<String,String> queryParameter = new HashMap<>();
            queryParameter.put("q","bitcoin");
            queryParameter.put("apiKey",apiKey);
            HttpResponse<String> jsonResponse =  ApiUtilis.get(baseUrl,queryParameter);
            NewsApiResponseDTO newsApiResponseDTO = objectMapper.readValue(jsonResponse.body(), NewsApiResponseDTO.class);
            List<ArticleDTO> articleDTOList = getUnifiedArticleDTO(newsApiResponseDTO);
            return articleDTOList;
        }
        catch(Exception exception){
            System.out.println("Got exception : ");
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @Override
    public void setBaseUrl(String url) {
        this.baseUrl = url;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey=apiKey;
    }

    private List<ArticleDTO> getUnifiedArticleDTO(NewsApiResponseDTO newsApiResponseDTO) {

        List<ArticleDTO> articleDTOList = new ArrayList<>();
        List<NewsApiArticleDTO> newsApiArticleDTOList = newsApiResponseDTO.getArticles();

        for(NewsApiArticleDTO newsApiArticleDTO : newsApiArticleDTOList){
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setTitle(newsApiArticleDTO.getTitle());
            articleDTO.setDescription(newsApiArticleDTO.getDescription());
            articleDTO.setContent(newsApiArticleDTO.getContent());
            articleDTO.setSource(newsApiArticleDTO.getSource() != null ? newsApiArticleDTO.getSource().getName() : null);
            articleDTO.setUrl(newsApiArticleDTO.getUrl());

            try {
                LocalDateTime publishedDate = LocalDateTime.parse(newsApiArticleDTO.getPublishedAt(), DateTimeFormatter.ISO_DATE_TIME);
                articleDTO.setPublishedDate(publishedDate);
            } catch (Exception e) {
                articleDTO.setPublishedDate(null);
            }
            articleDTOList.add(articleDTO);
        }
        return articleDTOList;
    }
}
