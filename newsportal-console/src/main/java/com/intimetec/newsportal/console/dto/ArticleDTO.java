package com.intimetec.newsportal.console.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleDTO {

    private Integer articleId;
    private String title;
    private String description;
    private String content;
    private String source;
    private String url;
    private LocalDateTime publishedDate;
    private List<String> categories;
    private Integer likesCount;
    private Integer dislikesCount;
    private Integer reportCount;
    private Boolean isVisible;

    public ArticleDTO() {
        categories = new ArrayList<>();
        this.likesCount = 0 ;
        this.dislikesCount = 0;
        this.reportCount = 0;
        this.isVisible = true;
    }

    public ArticleDTO(Integer articleId, String title, String description, String content,
                      String source, String url, LocalDateTime publishedDate, List<String> categories,Integer likesCount,Integer dislikesCount,Integer reportCount,Boolean isVisible) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.source = source;
        this.url = url;
        this.publishedDate = publishedDate;
        this.categories = categories;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.reportCount = reportCount;
        this.isVisible = isVisible;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(Integer dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public Integer getReportCount() {
        return reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", publishedDate=" + publishedDate +
                ", categories=" + categories +
                '}';
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
