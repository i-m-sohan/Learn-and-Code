package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.SaveOrRemoveArticleDTO;
import com.intimetec.newsportal.exception.EntityNotFoundException;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.UserSavedArticle;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.repository.UserSavedArticleRepository;
import com.intimetec.newsportal.service.SavedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleServiceImpl implements SavedArticleService {

    @Autowired
    UserSavedArticleRepository userSavedArticleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public void saveUserArticle(SaveOrRemoveArticleDTO saveUserArticleRequestDTO){
        Long userId = saveUserArticleRequestDTO.getUserId();
        Integer articleId = saveUserArticleRequestDTO.getArticleId();

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("User not found!");
        }
        User user = userOptional.get();

        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if(articleOptional.isEmpty()){
            throw new EntityNotFoundException("User not found!");
        }
        Article article = articleOptional.get();

        if (!userSavedArticleRepository.existsByUserAndArticle(user,article)) {
            UserSavedArticle savedArticle = new UserSavedArticle(user, article);
            userSavedArticleRepository.save(savedArticle);
        }
    }

    @Override
    public void deleteSavedArticle(SaveOrRemoveArticleDTO saveUserArticleRequestDTO){
        Long userId = saveUserArticleRequestDTO.getUserId();
        Integer articleId = saveUserArticleRequestDTO.getArticleId();
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("User not found!");
        }
        User user = userOptional.get();
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if(articleOptional.isEmpty()){
            throw new EntityNotFoundException("User not found!");
        }
        Article article = articleOptional.get();

        if (userSavedArticleRepository.existsByUserAndArticle(user,article)) {
            UserSavedArticle savedArticle = new UserSavedArticle(user, article);
            userSavedArticleRepository.delete(savedArticle);
        }
    }

    @Override
    public List<ArticleDTO> getSavedArticles(Long userId){
        User user = userRepository.getReferenceById(userId);
        List<UserSavedArticle> userSavedArticles = userSavedArticleRepository.findByUser(user);

        List<Article> articles = new ArrayList<>();

        for(UserSavedArticle userSavedArticle : userSavedArticles){
            articles.add(userSavedArticle.getArticle());
        }
        List<ArticleDTO> articleDTOList = ArticleMapper.toDTOList(articles);
        return articleDTOList;
    }
}
