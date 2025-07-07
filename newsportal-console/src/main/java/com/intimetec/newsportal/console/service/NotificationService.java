package com.intimetec.newsportal.console.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.intimetec.newsportal.console.app.session.UserSession;
import com.intimetec.newsportal.console.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.console.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.console.dto.NotificationDTO;
import com.intimetec.newsportal.console.exception.CategoryException;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private static final String BASE_URL = "http://localhost:8080/newsportal.intimetec.com/v1";

    private final ObjectMapper mapper;

    public NotificationService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<NotificationDTO> getUserNotifications() {
        try {
            Long userId = UserSession.getUserId();
            String url = BASE_URL + "/notification/" + userId;

            HttpResponse<String> response = ApiUtilis.get(url);
            NotificationDTO[] notificationArray = mapper.readValue(response.body(), NotificationDTO[].class);

            return Arrays.asList(notificationArray);
        } catch (Exception e) {
            System.out.println("⚠️ Error fetching notifications: " + e.getMessage());
            return List.of();
        }
    }

    public List<CategoryNotificationPreferenceDTO> getCategoryPreferences() {
        try {
            Long userId = UserSession.getUserId();
            HttpResponse<String> response = ApiUtilis.get(BASE_URL + "/category-preference/" + userId);
            Map<String, List<CategoryNotificationPreferenceDTO>> parsedReponse =
                    mapper.readValue(response.body(), new TypeReference<>() {});
            return parsedReponse.get("Category Preference List");
        } catch (Exception e) {
            System.out.println("⚠️ Error fetching category preferences: " + e.getMessage());
            return List.of();
        }
    }

    public void updateCategoryPreference(String categoryName, boolean isEnabled) {
        try {
            Long userId = UserSession.getUserId();

            Map<String, String> body = new HashMap<>();
            body.put("categoryName", categoryName);
            body.put("notificationsEnabled", String.valueOf(isEnabled));

            HttpResponse<String> response = ApiUtilis.patch(
                    BASE_URL + "/category-preference/" + userId,
                    body
            );

            int statusCode = response.statusCode();
            if(statusCode==404){
                throw new CategoryException("Category with name "+ categoryName + " Not found!");
            }
            if(statusCode!=200){
                throw new CategoryException("Unable to update the category preference for category : "+ categoryName);
            }
        }
        catch(CategoryException categoryException){
            throw categoryException;
        }
        catch (Exception e) {
            System.out.println("Failed to update category preference: " + e.getMessage());
        }
    }

    public void removeCategoryKeywords(CategoryKeywordDTO dto) {
        try {
            Long userId = UserSession.getUserId();
            String url = BASE_URL + "/user-category-keyword/" + userId;
            HttpResponse<String> response = ApiUtilis.delete(url, dto);
            int statusCode = response.statusCode();
            if(statusCode==404){
                throw new CategoryException("Category with name "+ dto.getCategoryName() + " Not found!");
            }
            if(statusCode!=201){
                throw new CategoryException("Unable to update the category preference for category : "+ dto.getCategoryName());
            }
        }
        catch(CategoryException categoryException){
            throw categoryException;
        }
        catch (Exception e) {
            System.out.println("⚠️ Failed to remove category keywords: " + e.getMessage());
        }
    }

    public void saveCategoryKeywords(CategoryKeywordDTO dto) {
        try {
            Long userId = UserSession.getUserId();
            String url = BASE_URL + "/user-category-keyword/" + userId;
            HttpResponse<String> response = ApiUtilis.post(url, dto);

            int statusCode = response.statusCode();
            if(statusCode==404){
                throw new CategoryException("Category with name "+ dto.getCategoryName() + " Not found!");
            }
            if(statusCode!=201){
                throw new CategoryException("Unable to update the category preference for category : "+ dto.getCategoryName());
            }
        }
        catch(CategoryException categoryException){
            throw categoryException;
        }
        catch (Exception e) {
            System.out.println("⚠️ Failed to save category keywords: " + e.getMessage());
        }
    }
}
