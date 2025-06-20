package com.intimetec.newsportal.dto;

import java.util.List;

public class CategoryKeywordDTO {

    private String categoryName;
    private List<String> keywords;

    public CategoryKeywordDTO() {}

    public CategoryKeywordDTO(Long userId, String categoryName, List<String> keywords) {
        this.categoryName = categoryName;
        this.keywords = keywords;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "UserCategoryKeywordDTO{" +
                "categoryName='" + categoryName + '\'' +
                ", keywords=" + keywords +
                '}';
    }
}

