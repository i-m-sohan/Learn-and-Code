package com.intimetec.newsportal.console.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

    private Long notificationId;
    private Long userId;
    private Integer articleId;
    private String message;
    private LocalDateTime createdAt;

    public NotificationDTO() {}

    public NotificationDTO(Long notificationId, Long userId, Integer articleId, String message, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.articleId = articleId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", articleId=" + articleId +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

