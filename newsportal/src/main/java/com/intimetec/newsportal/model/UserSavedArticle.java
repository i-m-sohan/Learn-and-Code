package com.intimetec.newsportal.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "userSavedArticle")
@IdClass(UserSavedArticle.UserSavedArticleId.class)
public class UserSavedArticle {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId", nullable = false)
    private Article article;

    public UserSavedArticle() {}

    public UserSavedArticle(User user, Article article) {
        this.user = user;
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public static class UserSavedArticleId implements Serializable {
        private Long user;
        private Integer article;

        public UserSavedArticleId() {}

        public UserSavedArticleId(Long user, Integer article) {
            this.user = user;
            this.article = article;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserSavedArticleId that)) return false;
            return Objects.equals(user, that.user) &&
                    Objects.equals(article, that.article);
        }

        @Override
        public int hashCode() {
            return Objects.hash(user, article);
        }
    }
}
