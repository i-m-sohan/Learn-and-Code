package com.intimetec.newsportal.dto;

public class SaveOrRemoveArticleDTO{
    private Long userId;
    private Integer articleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
}
