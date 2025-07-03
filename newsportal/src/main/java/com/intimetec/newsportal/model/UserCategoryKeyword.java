package com.intimetec.newsportal.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "UserCategoryKeyword",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "categoryId", "keyword_id"})
)
public class UserCategoryKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;
    public UserCategoryKeyword() {}

    public UserCategoryKeyword(User user, Category category, Keyword keyword) {
        this.user = user;
        this.category = category;
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }
}
