package com.intimetec.newsportal.dto;
import java.util.List;

public class ReportedArticleSummaryDTO {

    private Integer articleId;
    private String title;
    private String description;
    private String content;
    private int reportCount;
    private List<String> reportReasons;

    public ReportedArticleSummaryDTO() {}

    public ReportedArticleSummaryDTO(Integer articleId, String title, String description, String content,
                                     int reportCount, List<String> reportReasons) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.reportCount = reportCount;
        this.reportReasons = reportReasons;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public List<String> getReportReasons() {
        return reportReasons;
    }

    public void setReportReasons(List<String> reportReasons) {
        this.reportReasons = reportReasons;
    }
}
