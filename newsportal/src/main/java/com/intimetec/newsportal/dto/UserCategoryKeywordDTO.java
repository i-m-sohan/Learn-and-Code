package com.intimetec.newsportal.dto;

import java.util.List;

public class UserCategoryKeywordDTO {

    private Long userId;
    private Integer categoryId;
    private List<String> keywords;

    public UserCategoryKeywordDTO() {}

    public UserCategoryKeywordDTO(Long userId, Integer categoryId, List<String> keywords) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.keywords = keywords;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
