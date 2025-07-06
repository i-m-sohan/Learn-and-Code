package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.ArticleReactionRequestDTO;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Reaction;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.enums.ReactionType;

import java.time.LocalDateTime;

public class ReactionMapper {

    public static Reaction toEntity(ArticleReactionRequestDTO dto) {
        Reaction reaction = new Reaction();
        reaction.setUserId(dto.getUserId());
        reaction.setArticleId(dto.getArticleId());
        reaction.setReactionType(ReactionType.valueOf(dto.getReactionType().toUpperCase()));
        reaction.setReactedAt(LocalDateTime.now());
        return reaction;
    }

}

