package com.intimetec.newsportal.console.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.console.dto.CreateCategoryDTO;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class CategoryService {

    private static final String CATEGORY_URL = "http://localhost:8080/newsportal.intimetec.com/admin/category";

    public void createCategory(CreateCategoryDTO dto) {
        try {
            Map<String, String> body = Map.of("categoryName", dto.getCategoryName());

            HttpResponse<String> response = ApiUtilis.post(CATEGORY_URL, body);
            System.out.println("Category created successfully.");
        } catch (Exception e) {
            System.out.println("Failed to create category: " + e.getMessage());
        }
    }
}
