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

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "articleId", nullable = false)
    private Integer articleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType;

    @Column(nullable = false)
    private LocalDateTime reactedAt = LocalDateTime.now();

    // Getters and Setters

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
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
