package com.intimetec.newsportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CategoryKeyword", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"categoryId", "keyword"})
})
public class CategoryKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "keyword", referencedColumnName = "keyword", nullable = false)
    private Keyword keyword;

    public CategoryKeyword() {}

    public CategoryKeyword(Category category, Keyword keyword) {
        this.category = category;
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "CategoryKeyword{" +
                "id=" + id +
                ", categoryId=" + (category != null ? category.getCategoryId() : null) +
                ", keyword=" + (keyword != null ? keyword.getKeyword() : null) +
                '}';
    }
}
