package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.CategoryDTO;
import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    public Category toEntity(CreateCategoryDTO createCategoryDTO){
        Category category = new Category();
        category.setCategoryName(createCategoryDTO.getCategoryName());
        return category;
    }
    public CategoryDTO toDTO(Category category) {
        if (category == null) return null;

        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    public List<CategoryDTO> toDTOList(List<Category> categoryList){

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(Category category : categoryList){
            CategoryDTO categoryDTO = toDTO(category);
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }
}
