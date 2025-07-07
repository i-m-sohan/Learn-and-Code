package com.intimetec.newsportal.console.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.console.dto.CategoryDTO;
import com.intimetec.newsportal.console.dto.CategoryListResponseDTO;
import com.intimetec.newsportal.console.dto.CreateCategoryDTO;
import com.intimetec.newsportal.console.exception.CategoryException;
import com.intimetec.newsportal.console.utils.ApiUtilis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    private static final String CATEGORY_URL = "http://localhost:8080/newsportal.intimetec.com/category";

    @Autowired
    ObjectMapper objectMapper;

    public void createCategory(CreateCategoryDTO createCategoryDTO) {
        try {
            Map<String, String> body = Map.of("categoryName", createCategoryDTO.getCategoryName());
            String apiCallUrl = CATEGORY_URL + "/create";
            HttpResponse<String> response = ApiUtilis.post(apiCallUrl, body);
            int statusCode = response.statusCode();
            if(statusCode != 201){
                throw new CategoryException("Unable to create category : " + createCategoryDTO.getCategoryName()+ "With Message Body : " + response.body());
            }
            System.out.println("Category Created!");
        }
        catch(CategoryException categoryException){
            throw categoryException;
        }
        catch (Exception e) {
            System.out.println("Failed to create category: " + e.getMessage());
        }
    }

    public List<CategoryDTO> getAllCategories(){
        try {
            String apiCallUrl = CATEGORY_URL + "/all";
            HttpResponse<String> jsonResponse = ApiUtilis.get(apiCallUrl);
            CategoryListResponseDTO categoryListResponseDTO = objectMapper.readValue(jsonResponse.body(), CategoryListResponseDTO.class);
            int statusCode = jsonResponse.statusCode();
            if(statusCode!=200){
                throw new CategoryException("Unable to fetch categories!");
            }
            return categoryListResponseDTO.getCategories();
        }
        catch(CategoryException categoryException){
            throw categoryException;
        }
        catch (Exception e) {
            System.out.println("Failed to fetch categories : " + e.getMessage());
            return List.of();
        }
    }

    public void hideCategory(int categoryId) {
        try {
            String url = CATEGORY_URL + "/hide/" + categoryId;
            HttpResponse<String> jsonResponse = ApiUtilis.post(url);
            int statusCode = jsonResponse.statusCode();
            if(statusCode!=200){
                throw new CategoryException("Unable to fetch categories!");
            }
        }
        catch(CategoryException categoryException){
            throw categoryException;
        }
        catch (Exception e) {
            System.out.println("⚠️ Failed to hide category: " + e.getMessage());
        }
    }
}

