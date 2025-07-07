package com.intimetec.newsportal.console.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.intimetec.newsportal.console.app.session.UserSession;
import com.intimetec.newsportal.console.dto.*;
import com.intimetec.newsportal.console.enums.ReactionType;
import com.intimetec.newsportal.console.exception.ArticleExcpetion;
import com.intimetec.newsportal.console.exception.ArticleFetchFailedException;
import com.intimetec.newsportal.console.exception.ArticleSaveException;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

    private static final String BASE_URL = "http://localhost:8080/newsportal.intimetec.com/v1/article";

    @Autowired
    private ObjectMapper mapper;

    public ArticleService(){
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<ArticleDTO> getHeadlines(HeadlineRequestDTO headlineRequestDTO) {
        try {
            Map<String, String> body = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            body.put("startDate", headlineRequestDTO.getStartDate().format(formatter));
            body.put("endDate", headlineRequestDTO.getEndDate().format(formatter));
            body.put("category", headlineRequestDTO.getCategory());

            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/headlines/"+ UserSession.getUserId(), body);

            if(response.statusCode() != 200){
                throw new ArticleFetchFailedException("Error Occured : Headlines are not fetched, Response : " + response.body());
            }

            HeadlineResponseDTO headlineResponseDTO =  mapper.readValue(response.body(), HeadlineResponseDTO.class);
            List<ArticleDTO> articleDTOS =  headlineResponseDTO.getHeadlines();
            return articleDTOS;
        } catch (Exception e) {
            System.out.println("Error fetching articles: " + e.getMessage());
            return List.of();
        }
    }

    public void saveArticle(int articleId) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("articleId", String.valueOf(articleId));
            body.put("userId", String.valueOf(UserSession.getUserId()));
            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/save", body);
            int statusCode = response.statusCode();

            if(statusCode!=201){
                throw new ArticleSaveException("Unable to save article. ArticleId = " + articleId);
            }
            System.out.println("Article Saved Successfully!!");
        } catch (Exception e) {
            System.out.println("Failed to save article: " + e.getMessage());
        }
    }

    public void reactToArticle(int articleId, ReactionType reactionType) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("userId", String.valueOf(UserSession.getUserId()));
            body.put("articleId", String.valueOf(articleId));
            body.put("reactionType", reactionType.name());
            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/react", body);
            int statusCode = response.statusCode();
            if(statusCode!=200){
                throw new ArticleExcpetion("Unable to "+reactionType.name()+ "Article, ArticlId = "+articleId);
            }
            System.out.println(reactionType.name() + " : " + "Article Reacted");
        }
        catch(ArticleExcpetion articleExcpetion){
            throw articleExcpetion;
        }
        catch (Exception e) {
            System.out.println("Failed to " + reactionType.name().toLowerCase() + " article: " + e.getMessage());
        }
    }

    public void reportArticle(int articleId, String reason) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("userId", String.valueOf(UserSession.getUserId()));
            body.put("articleId", String.valueOf(articleId));
            body.put("reason", reason);

            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/report", body);
            int statusCode = response.statusCode();
            if(statusCode!=200){
                throw new ArticleExcpetion("Unable to report Article, ArticlId = " + articleId + "\n Reason : "+reason);
            }
            System.out.println("Article Reported!");
        }
        catch(ArticleExcpetion articleExcpetion){
            throw articleExcpetion;
        }
        catch (Exception e) {
            System.out.println("Failed to report article: " + e.getMessage());
        }
    }

    public List<ArticleDTO> getSavedArticles() {
        try {
            Long userId = UserSession.getUserId(); // get current logged-in user
            HttpResponse<String> response = ApiUtilis.get(BASE_URL + "/saved-articles/" + userId);
            SavedArticlesResponseDTO savedArticlesResponseDTO = mapper.readValue(response.body(),SavedArticlesResponseDTO.class);
            int statusCode = response.statusCode();
            if(statusCode!=200){
                throw new ArticleExcpetion("Unable to get User's Saved Articles ");
            }

            return savedArticlesResponseDTO.getSavedArticles();
        }
        catch(ArticleExcpetion articleExcpetion){
            throw articleExcpetion;
        }
        catch (Exception e) {
            System.out.println("Failed to fetch saved articles: " + e.getMessage());
            return List.of();
        }
    }

    public void removeSavedArticle(int articleId) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("userId", String.valueOf(UserSession.getUserId()));
            body.put("articleId", String.valueOf(articleId));

            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/delete", body);
            int statusCode = response.statusCode();
            if(statusCode!=201){
                throw new ArticleExcpetion("Unable to delete User's Saved Article, ArticleId : "+articleId);
            }
            System.out.println("Article Removed!");
        }
        catch(ArticleExcpetion articleExcpetion){
            throw articleExcpetion;
        }
        catch (Exception e) {
            System.out.println("Failed to delete saved article: " + e.getMessage());
        }
    }

    public List<ArticleDTO> searchArticles(String keyword) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("keyword", keyword);
            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/search", body);
            SearchArticlesResponseDTO searchResponse = mapper.readValue(response.body(), SearchArticlesResponseDTO.class);
            int statusCode = response.statusCode();
            if(statusCode!=200){
                throw new ArticleExcpetion("Unable to search Articles with keyword : "+keyword);
            }
            System.out.println("Article Saved Sucessfully!");
            return searchResponse.getArticles();
        }
        catch(ArticleExcpetion articleExcpetion){
            throw articleExcpetion;
        }
        catch (Exception e) {
            System.out.println("Error occurred while searching articles: " + e.getMessage());
            return List.of();
        }
    }

    public List<ReportedArticleSummaryDTO> getReportedArticlesSummary() {
        try {
            String url = BASE_URL + "/reported-summary";
            HttpResponse<String> response = ApiUtilis.get(url);

            ObjectMapper mapper = new ObjectMapper();
            List<ReportedArticleSummaryDTO> reportedArticleSummaryDTOS =  mapper.readValue(response.body(), new TypeReference<>() {});
            int statusCode = response.statusCode();
            if(statusCode!=200){
                throw new ArticleExcpetion("Unable to get Reported Article Summary");
            }
            System.out.println("Article Reported Sucessfully!");
            return reportedArticleSummaryDTOS;
        }
        catch(ArticleExcpetion articleExcpetion){
            throw articleExcpetion;
        }
        catch (Exception e) {
            System.out.println("Failed to fetch reported articles: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void hideArticle(int articleId) {
        try {
            String url = BASE_URL + "/admin/hide/"+articleId;
            HttpResponse<String> response = ApiUtilis.post(url);
            int statusCode = response.statusCode();
            if(statusCode != 200){
                throw new ArticleExcpetion("Unable to get Hide Article with id " + articleId);
            }
            System.out.println("Article Hidden Sucessfully!");
        }
        catch(ArticleExcpetion articleExcpetion){
            throw articleExcpetion;
        }
        catch (Exception e) {
            System.out.println("⚠️ Failed to fetch reported articles: " + e.getMessage());
        }
    }
}


