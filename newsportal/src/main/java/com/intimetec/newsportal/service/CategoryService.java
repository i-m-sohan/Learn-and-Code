package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.model.Category;

import java.util.List;

public interface CategoryService {
    public abstract List<Category> ensureCategoriesExist(List<String> categoryNames);
    public void createCategory(CreateCategoryDTO createCategoryDTO);
}
