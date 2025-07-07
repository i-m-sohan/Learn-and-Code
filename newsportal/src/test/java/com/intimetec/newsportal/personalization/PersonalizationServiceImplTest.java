package com.intimetec.newsportal.personalization;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.model.*;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.service.serviceImpl.PersonalizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonalizationServiceImplTest {

    @InjectMocks
    private PersonalizationServiceImpl personalizationServiceImpl;

    @Mock
    private ReportedArticleRepository reportedArticleRepository;

    @Mock
    private ArticleCategoryRepository articleCategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void computeReportContribution_returnsCorrectScore() throws Exception {
        Long userId = 99L;

        ArticleDTO article = new ArticleDTO();
        article.setArticleId(1);
        article.setCategories(List.of("Tech"));
        List<ArticleDTO> articles = List.of(article);

        ReportedArticle reported = new ReportedArticle();
        reported.setArticleId(1);
        reported.setUserId(userId);

        when(reportedArticleRepository.findByUserId(userId)).thenReturn(List.of(reported));

        ArticleCategory ac = new ArticleCategory();
        ac.setArticleId(1);
        ac.setCategoryId(100);

        when(articleCategoryRepository.findByArticleIdIn(List.of(1))).thenReturn(List.of(ac));

        Category category = new Category();
        category.setCategoryId(100);
        category.setCategoryName("Tech");

        when(categoryRepository.findAllById(List.of(100))).thenReturn(List.of(category));

        Method method = PersonalizationServiceImpl.class
                .getDeclaredMethod("computeReportContribution", Long.class, List.class);
        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<Integer, Long> result = (Map<Integer, Long>) method.invoke(personalizationServiceImpl, userId, articles);

        assertEquals(5L, result.get(1));
    }
}
