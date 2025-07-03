package com.intimetec.newsportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ReportedArticle")
public class ReportedArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer articleId;

    private String reason;

    @Column(nullable = false)
    private LocalDateTime reportedAt = LocalDateTime.now();

    public ReportedArticle() {}

    public ReportedArticle(Long userId, Integer articleId, String reason) {
        this.userId = userId;
        this.articleId = articleId;
        this.reason = reason;
        this.reportedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }
}
