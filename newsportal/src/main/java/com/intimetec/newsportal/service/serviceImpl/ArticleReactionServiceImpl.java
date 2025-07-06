package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleReactionRequestDTO;
import com.intimetec.newsportal.mapper.ReactionMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Reaction;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.enums.ReactionType;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.repository.ReactionRepository;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.ArticleReactionService;
import com.intimetec.newsportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleReactionServiceImpl implements ArticleReactionService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public void handleArticelReaction(ArticleReactionRequestDTO articleReactionRequestDTO) {

        Long userId = articleReactionRequestDTO.getUserId();
        Integer articleId = articleReactionRequestDTO.getArticleId();

        ReactionType newReactionType = ReactionType.valueOf(articleReactionRequestDTO.getReactionType().toUpperCase());
        Optional<Reaction> existingReactionOpt = reactionRepository.findByUserIdAndArticleId(userId, articleId);

        Reaction reaction;
        if (existingReactionOpt.isPresent()) {
            reaction = existingReactionOpt.get();
            if (isArticleReactionSame(reaction, newReactionType)) {
                return;
            }

            reaction.setReactionType(newReactionType);
            reaction.setReactedAt(LocalDateTime.now());
        } else {
            reaction = ReactionMapper.toEntity(articleReactionRequestDTO);
        }

        reactionRepository.save(reaction);
    }

    private boolean isArticleReactionSame(Reaction existingReaction, ReactionType newReactionType ){
        if(existingReaction.getReactionType() == newReactionType){
            return true;
        }
        return false;
    }
}
