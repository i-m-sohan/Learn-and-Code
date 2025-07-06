package com.intimetec.newsportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer articleId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String source;

    @Column(length = 500)
    private String url;

    private LocalDateTime publishedDate;

    @Column(nullable = false)
    private Integer likesCount;

    @Column(nullable = false)
    private Integer dislikesCount;

    @Column(nullable = false)
    private Integer reportCount;

    @Column(nullable = false)
    private boolean isVisible;

    @ManyToMany
    @JoinTable(
            name = "article_category",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories ;

    public Article() {
        categories = new HashSet<>();
        this.likesCount = 0;
        this.dislikesCount = 0;
        this.reportCount = 0;
        this.isVisible = true;
    }

    public Article(Integer articleId, String title, String description, String content, String source, String url,
                   LocalDateTime publishedDate, Integer likesCount, Integer dislikesCount, Integer reportCount, Boolean isVisible) {
        this.articleId = articleId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.source = source;
        this.url = url;
        this.publishedDate = publishedDate;
        this.likesCount = likesCount != null ? likesCount : 0;
        this.dislikesCount = dislikesCount != null ? dislikesCount : 0;
        this.reportCount = reportCount != null ? reportCount : 0;
        this.isVisible = isVisible != null ? isVisible : true;
        this.categories = new HashSet<>();
    }

    public Integer getArticleId() { return articleId; }
    public void setArticleId(Integer articleId) { this.articleId = articleId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public LocalDateTime getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }

    public Integer getLikesCount() { return likesCount; }
    public void setLikesCount(Integer likesCount) { this.likesCount = likesCount; }

    public Integer getDislikesCount() { return dislikesCount; }
    public void setDislikesCount(Integer dislikesCount) { this.dislikesCount = dislikesCount; }

    public Set<Category> getCategories() { return categories; }
    public void setCategories(Set<Category> categories) { this.categories = categories; }

    public Integer getReportCount() {
        return reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", publishedDate=" + publishedDate +
                ", likesCount=" + likesCount +
                ", dislikesCount=" + dislikesCount +
                ", categories=" + categories +
                '}';
    }
}
