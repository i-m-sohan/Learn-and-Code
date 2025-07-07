package com.intimetec.newsportal.keyword;

import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.exception.EntityNotFoundException;
import com.intimetec.newsportal.model.*;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.service.CategoryService;
import com.intimetec.newsportal.service.serviceImpl.KeywordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeywordServiceImplTest {

    @InjectMocks
    private KeywordServiceImpl keywordService;

    @Mock
    private CategoryKeywordRepository categoryKeywordRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private KeywordRepository keywordRepository;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCategoryKeywordRepository userCategoryKeywordRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveKeyword_shouldThrowExceptionIfCategoryMissing() {
        when(categoryRepository.findByCategoryName("Missing")).thenReturn(null);
        CategoryKeywordDTO dto = new CategoryKeywordDTO(null, "Missing", List.of("AI"));

        Exception e = assertThrows(EntityNotFoundException.class, () ->
                keywordService.saveUserCategoryKeyword(1L, dto));

        assertEquals("Category : Missing Not found!", e.getMessage());
    }

    @Test
    void saveKeyword_withOnlyNewKeywords_shouldCallSaveAllOnce() {
        User user = new User();
        Category category = new Category();

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(categoryRepository.findByCategoryName("Tech")).thenReturn(category);
        when(keywordRepository.findByKeywordIn(List.of("NewTerm"))).thenReturn(List.of());
        when(keywordRepository.saveAll(anyList()))
                .thenReturn(List.of(new Keyword("NewTerm")));
        when(userCategoryKeywordRepository.existsByUserAndCategoryAndKeyword(any(), any(), any()))
                .thenReturn(false);

        keywordService.saveUserCategoryKeyword(1L, new CategoryKeywordDTO(null, "Tech", List.of("NewTerm")));

        verify(keywordRepository).saveAll(anyList());
        verify(userCategoryKeywordRepository).save(any(UserCategoryKeyword.class));
    }

    @Test
    void saveKeyword_withMixedKeywords_shouldCallSaveAllOnce() {
        User user = new User();
        Category category = new Category();

        Keyword existing = new Keyword();
        existing.setKeyword("AI");

        Keyword savedNew = new Keyword();
        savedNew.setKeyword("ML");

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(categoryRepository.findByCategoryName("Tech")).thenReturn(category);
        when(keywordRepository.findByKeywordIn(List.of("AI", "ML"))).thenReturn(List.of(existing));
        when(keywordRepository.saveAll(anyList())).thenReturn(List.of(savedNew));
        when(userCategoryKeywordRepository.existsByUserAndCategoryAndKeyword(any(), any(), any()))
                .thenReturn(false);

        keywordService.saveUserCategoryKeyword(1L, new CategoryKeywordDTO(null, "Tech", List.of("AI", "ML")));

        verify(keywordRepository).saveAll(anyList());
        verify(userCategoryKeywordRepository, times(2)).save(any(UserCategoryKeyword.class));
    }

    @Test
    void removeKeyword_shouldThrowIfCategoryMissing() {
        when(categoryRepository.findByCategoryName("Invalid")).thenReturn(null);
        CategoryKeywordDTO dto = new CategoryKeywordDTO(null, "Invalid", List.of("AI"));

        Exception e = assertThrows(EntityNotFoundException.class, () ->
                keywordService.removeUserCategoryKeyword(1L, dto));

        assertEquals("Category : Invalid Not found!", e.getMessage());
    }

    @Test
    void removeKeyword_shouldDeleteMappedUserCategoryKeywords() {
        User user = new User();
        Category category = new Category();
        Keyword keyword = new Keyword();
        keyword.setKeyword("AI");

        List<UserCategoryKeyword> mappings = List.of(new UserCategoryKeyword());

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(categoryRepository.findByCategoryName("Tech")).thenReturn(category);
        when(keywordRepository.findByKeywordIn(List.of("AI"))).thenReturn(List.of(keyword));
        when(userCategoryKeywordRepository.findByUserAndCategoryAndKeywordIn(user, category, List.of(keyword)))
                .thenReturn(mappings);

        keywordService.removeUserCategoryKeyword(1L, new CategoryKeywordDTO(null, "Tech", List.of("AI")));

        verify(userCategoryKeywordRepository).deleteAll(mappings);
    }

    @Test
    void removeKeyword_withNoMatches_shouldNotCallDelete() {
        User user = new User();
        Category category = new Category();
        Keyword keyword = new Keyword("Spam");

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(categoryRepository.findByCategoryName("Tech")).thenReturn(category);
        when(keywordRepository.findByKeywordIn(List.of("Spam"))).thenReturn(List.of(keyword));
        when(userCategoryKeywordRepository.findByUserAndCategoryAndKeywordIn(user, category, List.of(keyword)))
                .thenReturn(List.of());

        keywordService.removeUserCategoryKeyword(1L, new CategoryKeywordDTO(null, "Tech", List.of("Spam")));

        verify(userCategoryKeywordRepository, never()).deleteAll(anyList());
    }
}
