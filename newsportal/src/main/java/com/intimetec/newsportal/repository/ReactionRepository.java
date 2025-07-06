package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Reaction;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByUserIdAndArticleId(Long userId, Integer articleId);

    void deleteByUserIdAndArticleId(Long userId, Integer articleId);

    List<Reaction> findByUserIdAndReactionType(Long userId, ReactionType reactionType);

    long countByArticleIdAndReactionType(Integer articleId, ReactionType reactionType);
}
