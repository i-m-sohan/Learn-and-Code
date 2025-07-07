package com.intimetec.newsportal.article;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.ArticleReportRequestDTO;
import com.intimetec.newsportal.dto.ReportedArticleDTO;
import com.intimetec.newsportal.dto.ReportedArticleSummaryDTO;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.mapper.ReportedArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.ArticleCategory;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.model.ReportedArticle;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.service.serviceImpl.ArticleFlagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ArticleFlagServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleCategoryRepository articleCategoryRepository;

    @Mock
    private ReportedArticleRepository reportedArticleRepository;

    @InjectMocks
    private ArticleFlagServiceImpl articleFlagService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void flagArticle_shouldSaveReportedArticle() {
        ArticleReportRequestDTO dto = new ArticleReportRequestDTO();
        dto.setArticleId(1);
        dto.setReason("Inappropriate");

        Article article = new Article();
        article.setArticleId(1);
        article.setReportCount(4);

        Category category = new Category();
        category.setCategoryId(1);

        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setArticleId(1);
        articleCategory.setCategoryId(1);

        when(articleRepository.getReferenceById(1)).thenReturn(article);
        when(articleCategoryRepository.findByArticleId(1)).thenReturn(List.of(articleCategory));
        when(categoryRepository.findAllById(Set.of(1))).thenReturn(List.of(category));
        when(reportedArticleRepository.save(any())).thenReturn(new ReportedArticle());
        when(articleRepository.save(any())).thenReturn(article);

        articleFlagService.flagArticle(dto);

        verify(reportedArticleRepository).save(any());
        verify(articleRepository).save(any());
    }

    @Test
    void getReportedArticleSummary_shouldReturnEmpty_whenNoReports() {
        when(articleRepository.findVisibleReportedArticles(0)).thenReturn(Collections.emptyList());

        List<ReportedArticleSummaryDTO> result = articleFlagService.getReportedArticleSummary();

        assertTrue(result.isEmpty());
    }
}
