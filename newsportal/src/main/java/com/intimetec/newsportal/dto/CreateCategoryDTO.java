package com.intimetec.newsportal.dto;

public class CreateCategoryDTO {
    String categoryName;

    public CreateCategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
