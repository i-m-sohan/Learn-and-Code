package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CreateCategoryDTO createCategoryDTO){
        Category category = new Category();
        category.setCategoryName(createCategoryDTO.getCategoryName());
        return category;
    }
}
