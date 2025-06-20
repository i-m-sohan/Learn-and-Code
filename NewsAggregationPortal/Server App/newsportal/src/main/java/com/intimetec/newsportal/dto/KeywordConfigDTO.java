package com.intimetec.newsportal.dto;

import java.util.List;

public class KeywordConfigDTO {
    private int userId;
    private List<String> keywords;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
