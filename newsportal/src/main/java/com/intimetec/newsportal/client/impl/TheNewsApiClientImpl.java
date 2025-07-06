package com.intimetec.newsportal.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.TheNewsApiArticleDTO;
import com.intimetec.newsportal.dto.TheNewsApiResponseDTO;
import com.intimetec.newsportal.utils.ApiUtilis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("The News API")
public class TheNewsApiClientImpl implements NewsClient {

    public String baseUrl;
    public String apiKey;

    @Autowired
    private ObjectMapper objectMapper;

    public TheNewsApiClientImpl(){
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<ArticleDTO> getArticlesPeriodically(){
        try {
            baseUrl += "/all";
            Map<String,String> queryParameter = new HashMap<>();
            queryParameter.put("api_token",apiKey);
            queryParameter.put("language","en");
            HttpResponse<String> jsonResponse =  ApiUtilis.get(baseUrl,queryParameter);
//            System.out.println(jsonResponse);
            TheNewsApiResponseDTO theNewsApiResponseDTO = objectMapper.readValue(jsonResponse.body(), TheNewsApiResponseDTO.class);
            List<ArticleDTO> articleDTOList = getUnifiedArticleDTO(theNewsApiResponseDTO);

            System.out.println("Printing Response from server : The News Api");
            for (ArticleDTO articleDTO : articleDTOList) {
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("articleId       = " + articleDTO.getArticleId());
                System.out.println("title           = " + articleDTO.getTitle());
                System.out.println("description     = " + articleDTO.getDescription());
                System.out.println("content         = " + articleDTO.getContent());
                System.out.println("source          = " + articleDTO.getSource());
                System.out.println("url             = " + articleDTO.getUrl());
                System.out.println("publishedDate   = " + articleDTO.getPublishedDate());
                System.out.println("likesCount      = " + articleDTO.getLikesCount());
                System.out.println("dislikesCount   = " + articleDTO.getDislikesCount());
                System.out.println("categories      = " + articleDTO.getCategories());
                System.out.println("-----------------------------------------------------------------------");
            }

            return articleDTOList;
        }
        catch(Exception exception){
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
        this.apiKey = apiKey;
    }

    private List<ArticleDTO> getUnifiedArticleDTO(TheNewsApiResponseDTO theNewsApiResponseDTO){
        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for(TheNewsApiArticleDTO theNewsApiArticleDTO : theNewsApiResponseDTO.getData()){
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setTitle(theNewsApiArticleDTO.getTitle());
            articleDTO.setDescription(theNewsApiArticleDTO.getDescription());
            articleDTO.setContent(theNewsApiArticleDTO.getSnippet());
            articleDTO.setSource(theNewsApiArticleDTO.getSource());
            articleDTO.setPublishedDate(theNewsApiArticleDTO.getPublishedAt());
            articleDTO.setUrl(theNewsApiArticleDTO.getUrl());
            articleDTO.setCategories(theNewsApiArticleDTO.getCategories());
            articleDTOList.add(articleDTO);
        }
        return articleDTOList;
    }
}
