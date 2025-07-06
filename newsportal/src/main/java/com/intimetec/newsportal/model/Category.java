package com.intimetec.newsportal.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private Set<Article> articles = new HashSet<>();

    @Column(nullable = false)
    private boolean isVisible;

    public Category() {
        isVisible=true;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Set<Article> getArticles() { return articles; }
    public void setArticles(Set<Article> articles) { this.articles = articles; }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", articles=" + articles +
                ", isVisible=" + isVisible +
                '}';
    }
}
