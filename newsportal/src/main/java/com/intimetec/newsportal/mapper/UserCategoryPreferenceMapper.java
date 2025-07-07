package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.UserCategoryPreference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class UserCategoryPreferenceMapper {

    public CategoryNotificationPreferenceDTO toDTO(UserCategoryPreference preference, String categoryName) {
        CategoryNotificationPreferenceDTO dto = new CategoryNotificationPreferenceDTO();
        dto.setCategoryName(categoryName); // This assumes you'll fetch name externally
        dto.setNotificationsEnabled(preference.getNotificationsEnabled());
        return dto;
    }

    public UserCategoryPreference toEntity(Long userId, Integer categoryId, CategoryNotificationPreferenceDTO dto) {
        return new UserCategoryPreference(userId, categoryId, dto.isNotificationsEnabled());
    }
}
