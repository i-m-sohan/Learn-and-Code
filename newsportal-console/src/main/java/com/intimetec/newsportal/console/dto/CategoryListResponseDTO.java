package com.intimetec.newsportal.console.dto;

import java.util.List;

public class CategoryListResponseDTO {
    private List<CategoryDTO> categories;

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
