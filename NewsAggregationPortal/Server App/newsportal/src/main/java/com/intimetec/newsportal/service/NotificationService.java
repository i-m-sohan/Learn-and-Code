package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.model.Article;

import java.util.List;

public interface NotificationService {
    public void notifyUsersForMatchingArticles(List<Article> articleList);
    public void updateUserCategoryPreference(Long userId, CategoryNotificationPreferenceDTO categoryNotificationPreferenceDTO);
}
