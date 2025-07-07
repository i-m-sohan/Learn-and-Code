package com.intimetec.newsportal.article;

import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.exception.EntitySaveException;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.factory.NewsProviderFactory;
import com.intimetec.newsportal.service.serviceImpl.ArticleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DuplicateKeyException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private NewsProviderFactory newsProviderFactory;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveArticles_shouldReturnSavedArticles_whenInputIsValid() {
        ArticleDTO dto = new ArticleDTO();
        dto.setArticleId(1);
        dto.setCategories(List.of("Tech"));

        Category category = new Category();
        category.setCategoryName("Tech");
        category.setVisible(true);

        Article article = new Article();
        article.setArticleId(1);

        when(categoryRepository.findByCategoryNameIn(any())).thenReturn(List.of());
        when(categoryRepository.saveAll(any())).thenReturn(List.of(category));
        when(articleRepository.saveAll(any())).thenReturn(List.of(article));

        List<Article> result = articleService.saveArticles(List.of(dto));

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getArticleId());
    }

    @Test
    void saveArticles_shouldThrowException_whenSaveFails() {
        ArticleDTO dto = new ArticleDTO();
        dto.setCategories(List.of("Tech"));

        Category category = new Category();
        category.setCategoryName("Tech");

        when(categoryRepository.findByCategoryNameIn(any())).thenReturn(List.of());
        when(categoryRepository.saveAll(any())).thenReturn(List.of(category));
        when(articleRepository.saveAll(any())).thenThrow(new DuplicateKeyException("Simulated error"));

        assertThrows(EntitySaveException.class, () -> articleService.saveArticles(List.of(dto)));
    }

    @Test
    void hidArticle_shouldSetVisibilityFalse() {
        Article article = new Article();
        article.setArticleId(5);
        article.setVisible(true);

        when(articleRepository.getReferenceById(5)).thenReturn(article);

        articleService.hidArticle(5);

        verify(articleRepository).save(article);
        assertFalse(article.isVisible());
    }

    @Test
    void hideArticleByCategory_shouldHideAllVisibleArticles() {
        Article article1 = new Article();
        article1.setVisible(true);
        Article article2 = new Article();
        article2.setVisible(true);

        List<Article> articles = List.of(article1, article2);

        when(articleRepository.findVisibleArticlesByCategoryId(100)).thenReturn(articles);

        articleService.hideArticleByCategory(100);

        verify(articleRepository).saveAll(articles);
        assertFalse(article1.isVisible());
        assertFalse(article2.isVisible());
    }

    @Test
    void fetchArticleFromExternalSources_shouldReturnCombinedArticles() {
        NewsClient client1 = mock(NewsClient.class);
        NewsClient client2 = mock(NewsClient.class);

        ArticleDTO dto1 = new ArticleDTO();
        ArticleDTO dto2 = new ArticleDTO();

        when(client1.getArticlesPeriodically()).thenReturn(List.of(dto1));
        when(client2.getArticlesPeriodically()).thenReturn(List.of(dto2));
        when(newsProviderFactory.getAvailableNewsClients()).thenReturn(List.of(client1, client2));

        List<ArticleDTO> result = articleService.fetchArticleFromExternalSources();

        assertEquals(2, result.size());
    }

    @Test
    void fetchArticleFromExternalSources_shouldSkipNullClients() {
        NewsClient client1 = mock(NewsClient.class);

        when(client1.getArticlesPeriodically()).thenReturn(null);
        when(newsProviderFactory.getAvailableNewsClients()).thenReturn(List.of(client1));

        List<ArticleDTO> result = articleService.fetchArticleFromExternalSources();

        assertTrue(result.isEmpty());
    }
}
