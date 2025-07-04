package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Reaction;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByUserAndArticle(User user, Article article);

    void deleteByUserAndArticle(User user, Article article);

    long countByArticleAndReactionType(Article article, com.intimetec.newsportal.model.enums.ReactionType reactionType);
}
