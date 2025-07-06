package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.dto.NotificationDTO;
import com.intimetec.newsportal.model.Article;

import java.util.List;

public interface NotificationService {
    public void notifyUsersForMatchingArticles(List<ArticleDTO> articleDTOList);
    public void updateUserCategoryPreference(Long userId, CategoryNotificationPreferenceDTO categoryNotificationPreferenceDTO);
    public List<NotificationDTO> getUserNotifications(Long userId);
}
