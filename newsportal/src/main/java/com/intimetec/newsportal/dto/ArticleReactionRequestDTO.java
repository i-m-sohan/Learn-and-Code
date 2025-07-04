package com.intimetec.newsportal.dto;

public class ArticleReactionRequestDTO {
    private Long userId;
    private Integer articleId;
    private String reactionType; // Expected values: "LIKE" or "DISLIKE"

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

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }
}
