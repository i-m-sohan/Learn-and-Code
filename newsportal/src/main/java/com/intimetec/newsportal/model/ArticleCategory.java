package com.intimetec.newsportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "article_category", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"article_id", "category_id"})
})
public class ArticleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_category_id")
    private Integer articleCategoryId;

    @Column(name = "article_id", nullable = false)
    private Integer articleId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    public ArticleCategory() {
    }

    public ArticleCategory(Integer articleId, Integer categoryId) {
        this.articleId = articleId;
        this.categoryId = categoryId;
    }

    public Integer getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Integer articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
