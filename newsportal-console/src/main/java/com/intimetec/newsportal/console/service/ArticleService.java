package com.intimetec.newsportal.console.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.intimetec.newsportal.console.dto.ArticleDTO;
import com.intimetec.newsportal.console.dto.HeadlineRequestDTO;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {

    private static final String BASE_URL = "http://localhost:8080/newsportal.intimetec.com/v1";

    public List<ArticleDTO> getArticlesByHeadlineRequest(HeadlineRequestDTO dto) {
        try {
            Map<String, String> body = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            body.put("startDate", dto.getStartDate().format(formatter));
            body.put("endDate", dto.getEndDate().format(formatter));
            body.put("category", dto.getCategory());

            HttpResponse<String> response = ApiUtilis.post(BASE_URL + "/headlines", body);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.readValue(response.body(), new TypeReference<List<ArticleDTO>>() {});
        } catch (Exception e) {
            System.out.println("Error fetching articles: " + e.getMessage());
            return List.of();
        }
    }
    public void fetchUserNotifications(Long userId) {
        try {
            String endpoint = BASE_URL + "/notification/" + userId;
            String response = ApiUtilis.sendGetRequest(endpoint);
            System.out.println("🔔 Your Notifications:");
            System.out.println(response);
        } catch (Exception e) {
            System.out.println("⚠️ Failed to fetch notifications: " + e.getMessage());
        }
    }


}


