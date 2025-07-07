package com.intimetec.newsportal.category;

import com.intimetec.newsportal.dto.CategoryDTO;
import com.intimetec.newsportal.mapper.CategoryMapper;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.CategoryKeywordRepository;
import com.intimetec.newsportal.repository.CategoryRepository;
import com.intimetec.newsportal.repository.KeywordRepository;
import com.intimetec.newsportal.service.ArticleService;
import com.intimetec.newsportal.service.serviceImpl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ArticleService articleService;

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private CategoryKeywordRepository categoryKeywordRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl();

        // Inject mocks into private fields
        ReflectionTestUtils.setField(categoryService, "categoryMapper", categoryMapper);
        ReflectionTestUtils.setField(categoryService, "categoryRepository", categoryRepository);
        ReflectionTestUtils.setField(categoryService, "articleService", articleService);
        ReflectionTestUtils.setField(categoryService, "keywordRepository", keywordRepository);
        ReflectionTestUtils.setField(categoryService, "categoryKeywordRepository", categoryKeywordRepository);
    }

    // ✅ Test getAllCategories - Success
    @Test
    void getAllCategories_shouldReturnMappedCategories() {
        List<Category> categories = List.of(new Category("Tech"), new Category("Sports"));
        List<CategoryDTO> categoryDTOs = List.of(new CategoryDTO("Tech"), new CategoryDTO("Sports"));

        when(categoryRepository.findByIsVisibleTrue()).thenReturn(categories);
        when(categoryMapper.toDTOList(categories)).thenReturn(categoryDTOs);

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
    }

    // ✅ Test createCategory - Saves Correctly
    @Test
    void createCategory_shouldSaveCategory() {
        CategoryDTO dto = new CategoryDTO("Politics");
        Category entity = new Category("Politics");

        when(categoryMapper.toEntity(any())).thenReturn(entity);

        categoryService.createCategory(new com.intimetec.newsportal.dto.CreateCategoryDTO("Politics"));

        verify(categoryRepository, times(1)).save(entity);
    }

    // ✅ Test hideCategory - Sets visibility and hides articles
    @Test
    void hideCategory_shouldSetInvisibleAndCallArticleService() {
        Category category = new Category("Science");
        category.setCategoryId(1);
        category.setVisible(true);

        when(categoryRepository.getReferenceById(1)).thenReturn(category);

        categoryService.hideCategory(1);

        assertFalse(category.isVisible());
        verify(articleService).hideArticleByCategory(1);
    }
}
