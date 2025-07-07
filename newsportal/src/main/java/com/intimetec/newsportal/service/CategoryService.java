package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.CategoryDTO;
import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.model.Category;

import java.util.List;

public interface CategoryService {
    public abstract List<Category> ensureCategoriesExist(List<String> categoryNames);
    public void createCategory(CreateCategoryDTO createCategoryDTO);
    public void hideCategory(Integer categoryId);
    public void defineArticlesCategory(List<ArticleDTO> articleDTOList);
    public List<CategoryDTO> getAllCategories();
}
