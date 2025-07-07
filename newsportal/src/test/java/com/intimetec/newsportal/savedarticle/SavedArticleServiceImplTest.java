package com.intimetec.newsportal.savedarticle;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.SaveOrRemoveArticleDTO;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.UserSavedArticle;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.repository.UserSavedArticleRepository;
import com.intimetec.newsportal.service.serviceImpl.SavedArticleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SavedArticleServiceImplTest {

    @Mock
    private UserSavedArticleRepository userSavedArticleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private SavedArticleServiceImpl savedArticleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUserArticle_savesIfNotExists() {
        SaveOrRemoveArticleDTO dto = new SaveOrRemoveArticleDTO();
        dto.setUserId(1L);
        dto.setArticleId(101);

        User user = new User();
        Article article = new Article();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(articleRepository.findById(101)).thenReturn(Optional.of(article));
        when(userSavedArticleRepository.existsByUserAndArticle(user, article)).thenReturn(false);

        savedArticleService.saveUserArticle(dto);

        verify(userSavedArticleRepository, times(1)).save(any(UserSavedArticle.class));
    }

    @Test
    void testDeleteSavedArticle_deletesIfExists() {
        SaveOrRemoveArticleDTO dto = new SaveOrRemoveArticleDTO();
        dto.setUserId(1L);
        dto.setArticleId(101);

        User user = new User();
        Article article = new Article();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(articleRepository.findById(101)).thenReturn(Optional.of(article));
        when(userSavedArticleRepository.existsByUserAndArticle(user, article)).thenReturn(true);

        savedArticleService.deleteSavedArticle(dto);

        verify(userSavedArticleRepository, times(1)).delete(any(UserSavedArticle.class));
    }

    @Test
    void testGetSavedArticles_returnsList() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Article article = new Article();
        article.setArticleId(101);

        UserSavedArticle userSavedArticle = new UserSavedArticle();
        userSavedArticle.setUser(user);
        userSavedArticle.setArticle(article);

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(userSavedArticleRepository.findByUser(user)).thenReturn(Collections.singletonList(userSavedArticle));

        List<ArticleDTO> result = savedArticleService.getSavedArticles(userId);

        assertEquals(1, result.size());
    }
}
