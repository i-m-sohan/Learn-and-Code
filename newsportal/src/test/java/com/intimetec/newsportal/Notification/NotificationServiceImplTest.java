package com.intimetec.newsportal.Notification;

import com.intimetec.newsportal.dto.*;
import com.intimetec.newsportal.exception.EntityNotFoundException;
import com.intimetec.newsportal.mapper.UserCategoryPreferenceMapper;
import com.intimetec.newsportal.model.*;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.service.CategoryService;
import com.intimetec.newsportal.service.EmailService;
import com.intimetec.newsportal.service.serviceImpl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock private UserRepository userRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private UserCategoryPreferenceRepository userCategoryPreferenceRepository;
    @Mock private UserCategoryPreferenceMapper userCategoryPreferenceMapper;
    @Mock private CategoryService categoryService;
    @Mock private EmailService emailService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updatePreference_shouldUpdateFlag() {
        User user = new User();
        Category category = new Category();
        category.setCategoryId(10);
        UserCategoryPreference preference = new UserCategoryPreference();

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(categoryRepository.findByCategoryName("Tech")).thenReturn(category);
        when(userCategoryPreferenceRepository.findByUserIdAndCategoryId(1L, 10)).thenReturn(preference);

        CategoryNotificationPreferenceDTO dto = new CategoryNotificationPreferenceDTO();
        dto.setCategoryName("Tech");
        dto.setNotificationsEnabled(true);

        notificationService.updateUserCategoryPreference(1L, dto);
        assertTrue(preference.getNotificationsEnabled());
    }

    @Test
    void updatePreference_shouldThrowIfCategoryNotFound() {
        when(categoryRepository.findByCategoryName("Unknown")).thenReturn(null);

        CategoryNotificationPreferenceDTO dto = new CategoryNotificationPreferenceDTO();
        dto.setCategoryName("Unknown");

        Exception e = assertThrows(EntityNotFoundException.class,
                () -> notificationService.updateUserCategoryPreference(1L, dto));

        assertEquals("Category with name : Unknown", e.getMessage());
    }

    @Test
    void getUserPreferences_shouldReturnMappedList() {
        User user = new User();
        user.setId(1L);
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Tech");

        UserCategoryPreference pref = new UserCategoryPreference();
        pref.setCategoryId(1);

        CategoryNotificationPreferenceDTO mappedDTO = new CategoryNotificationPreferenceDTO();
        mappedDTO.setCategoryName("Tech");
        mappedDTO.setNotificationsEnabled(false);

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(userCategoryPreferenceRepository.findByUserId(1L)).thenReturn(List.of(pref));
        when(categoryRepository.findAllById(List.of(1))).thenReturn(List.of(category));
        when(userCategoryPreferenceMapper.toDTO(pref, "Tech")).thenReturn(mappedDTO);

        List<CategoryNotificationPreferenceDTO> result = notificationService.getUserCategoryPreferences(1L);

        assertEquals(1, result.size());
    }

    @Test
    void createCategoryPreferences_shouldSaveForAllCategories() {
        User user = new User();
        user.setId(1L);

        CategoryDTO cat1 = new CategoryDTO();
        cat1.setCategoryId(1);
        CategoryDTO cat2 = new CategoryDTO();
        cat2.setCategoryId(2);

        when(userRepository.getReferenceById(1L)).thenReturn(user);
        when(categoryService.getAllCategories()).thenReturn(List.of(cat1, cat2));

        notificationService.createCategoryNotificationPreferences(1L);

        ArgumentCaptor<List<UserCategoryPreference>> captor = ArgumentCaptor.forClass(List.class);
        verify(userCategoryPreferenceRepository).saveAll(captor.capture());

        assertEquals(2, captor.getValue().size());
    }
}
