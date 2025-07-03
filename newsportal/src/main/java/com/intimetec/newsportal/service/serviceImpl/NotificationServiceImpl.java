package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.dto.NotificationDTO;
import com.intimetec.newsportal.mapper.NotificationMapper;
import com.intimetec.newsportal.model.*;
import com.intimetec.newsportal.repository.CategoryRepository;
import com.intimetec.newsportal.repository.NotificationRepository;
import com.intimetec.newsportal.repository.UserCategoryPreferenceRepository;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.EmailService;
import com.intimetec.newsportal.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserCategoryPreferenceRepository userCategoryPreferenceRepository;

    @Autowired
    NotificationRepository notificationRepository;

    public void updateUserCategoryPreference(Long userId,CategoryNotificationPreferenceDTO categoryNotificationPreferenceDTO){
        User user = userRepository.getReferenceById(userId);
        Category category = categoryRepository.findByCategoryName(categoryNotificationPreferenceDTO.getCategoryName());

        UserCategoryPreference userCategoryPreference = new UserCategoryPreference();
        userCategoryPreference.setUser(user);
        userCategoryPreference.setCategory(category);
        userCategoryPreferenceRepository.save(userCategoryPreference);
    }

    @Transactional
    @Override
    public void notifyUsersForMatchingArticles(List<Article> articleList) {

        Map<User, Set<Article>> userToArticlesMap = mapUsersToNotifiableArticles(articleList);
        List<Notification> notificationList = new ArrayList<>();

        for (User user : userToArticlesMap.keySet()) {
            Set<Article> articles = userToArticlesMap.get(user);
            if (articles != null && !articles.isEmpty()) {
                String mailBody = "";
                for (Article article : articles) {
                    Notification notification = new Notification();
                    notification.setArticle(article);
                    notification.setUser(user);
                    notification.setCreatedAt(LocalDateTime.now());
                    notification.setMessage(article.getTitle());
                    notificationList.add(notification);
                    mailBody += "Article Title : " + article.getTitle() + "\n" + "Article URL : " + article.getUrl() + "\n";
                }
                emailService.sendEmail(user.getEmail(), "Latest Articles Based on Your Preferences", mailBody);
            }
        }
        notificationRepository.saveAll(notificationList);
    }

    private Map<User, Set<Article>> mapUsersToNotifiableArticles(List<Article> articleList){
        Map<Integer, Set<Article>> categoryIdToArticleList = new HashMap<>();
        if (!articleList.isEmpty()) {
            for (Article article : articleList) {
                Set<Category> categorySet = article.getCategories();
                if (categorySet != null && !categorySet.isEmpty()) {
                    for (Category category : categorySet) {
                        Integer categoryId = category.getCategoryId();
                        categoryIdToArticleList.computeIfAbsent(categoryId, k -> new HashSet<>()).add(article);
                    }
                }
            }
        }

        List<UserCategoryPreference> userCategoryPreferenceList = userCategoryPreferenceRepository.findAll();

        Map<User, Set<Integer>> userIdToCategoryIdsMap = new HashMap<>();
        for (UserCategoryPreference userCategoryPreference : userCategoryPreferenceList) {
            User user = userCategoryPreference.getUser();
            userIdToCategoryIdsMap.putIfAbsent(user, new HashSet<>());
            if (userCategoryPreference.getNotificationsEnabled()) {
                userIdToCategoryIdsMap.get(user).add(userCategoryPreference.getCategory().getCategoryId());
            }
        }

        Set<User> userSet = userIdToCategoryIdsMap.keySet();
        Map<User, Set<Article>> userToArticlesMap = new HashMap<>();

        for (User user : userSet) {
            Set<Article> articleSet = new HashSet<>();
            for (Integer categoryId : userIdToCategoryIdsMap.get(user)) {
                if (categoryIdToArticleList.containsKey(categoryId)) {
                    articleSet.addAll(categoryIdToArticleList.get(categoryId));
                }
            }
            userToArticlesMap.put(user, articleSet);
        }
        return userToArticlesMap;
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        List<Notification> notificationList = notificationRepository.findByUser_Id(userId);
        List<NotificationDTO> notificationDTOList = NotificationMapper.toDTOList(notificationList);
        return notificationDTOList;
    }

}
