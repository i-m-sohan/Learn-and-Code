package com.intimetec.newsportal.dto;
import java.time.LocalDateTime;

public class ReportedArticleDTO {

    private Long reportId;
    private Long userId;
    private Integer articleId;
    private String reason;
    private LocalDateTime reportedAt;

    public ReportedArticleDTO() {}

    public ReportedArticleDTO(Long reportId, Long userId, Integer articleId, String reason, LocalDateTime reportedAt) {
        this.reportId = reportId;
        this.userId = userId;
        this.articleId = articleId;
        this.reason = reason;
        this.reportedAt = reportedAt;
    }
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
