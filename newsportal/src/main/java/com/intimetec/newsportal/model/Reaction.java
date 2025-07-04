package com.intimetec.newsportal.model;
import com.intimetec.newsportal.model.enums.ReactionType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reaction", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "articleId"})
})
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleId", nullable = false)
    private Article article;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType;

    @Column(nullable = false)
    private LocalDateTime reactedAt = LocalDateTime.now();

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
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

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public LocalDateTime getReactedAt() {
        return reactedAt;
    }

    public void setReactedAt(LocalDateTime reactedAt) {
        this.reactedAt = reactedAt;
    }
}

