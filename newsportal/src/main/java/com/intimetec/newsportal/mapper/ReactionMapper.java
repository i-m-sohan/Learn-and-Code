package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.ArticleReactionRequestDTO;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Reaction;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.enums.ReactionType;

import java.time.LocalDateTime;

public class ReactionMapper {

    public static Reaction toEntity(ArticleReactionRequestDTO dto, User user, Article article) {
        Reaction reaction = new Reaction();
        reaction.setUser(user);
        reaction.setArticle(article);
        reaction.setReactionType(ReactionType.valueOf(dto.getReactionType().toUpperCase()));
        reaction.setReactedAt(LocalDateTime.now());
        return reaction;
    }

}
