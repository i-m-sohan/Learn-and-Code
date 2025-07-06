package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.dto.NotificationDTO;
import com.intimetec.newsportal.mapper.NotificationMapper;
import com.intimetec.newsportal.model.*;
import com.intimetec.newsportal.repository.*;
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
    UserCategoryKeywordRepository userCategoryKeywordRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    CategoryKeywordRepository categoryKeywordRepository;

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
    public void notifyUsersForMatchingArticles(List<ArticleDTO> articleDTOList) {
        System.out.println("🔍 Starting user notification process for matching articles...");

        List<String> categoryNames = new ArrayList<>();
        for (ArticleDTO articleDTO : articleDTOList) {
            List<String> currentArticleCategoryNames = articleDTO.getCategories();
            if (currentArticleCategoryNames != null && !currentArticleCategoryNames.isEmpty()) {
                for (String categoryName : currentArticleCategoryNames) {
                    if (categoryName != null && !categoryNames.contains(categoryName)) {
                        categoryNames.add(categoryName);
                    }
                }
            }
        }

        System.out.println("🧾 Extracted visible category names from articles: " + categoryNames);

        List<Category> categoryList = categoryRepository.findByCategoryNameInAndIsVisibleTrue(categoryNames);
        System.out.println("✅ Retrieved visible Category entities from DB: " + categoryList.size());

        Map<String, Category> nameToCategoryMap = new HashMap<>();
        for (Category category : categoryList) {
            if (category != null && !nameToCategoryMap.containsKey(category.getCategoryName())) {
                nameToCategoryMap.put(category.getCategoryName(), category);
            }
        }

        List<CategoryKeyword> categoryKeywordList = categoryKeywordRepository.findByCategoryIn(categoryList);
        System.out.println("🔑 Retrieved CategoryKeywords for categories: " + categoryKeywordList.size());

        List<UserCategoryKeyword> userCategoryKeywordList = userCategoryKeywordRepository.findByCategoryIn(categoryList);
        System.out.println("👥 Retrieved UserCategoryKeywords for categories: " + userCategoryKeywordList.size());

        Map<User, List<ArticleDTO>> userToArticlesMap = new HashMap<>();

        for (UserCategoryKeyword userCategoryKeyword : userCategoryKeywordList) {
            Keyword keyword = userCategoryKeyword.getKeyword();
            User user = userCategoryKeyword.getUser();
            Category category = userCategoryKeyword.getCategory();

            String keywordValue = keyword.getKeyword();
            for (ArticleDTO articleDTO : articleDTOList) {
                String title = articleDTO.getTitle() != null ? articleDTO.getTitle() : "";
                String description = articleDTO.getDescription() != null ? articleDTO.getDescription() : "";
                String content = articleDTO.getContent() != null ? articleDTO.getContent() : "";
                String articleCombinedText = (title + " " + description + " " + content).toLowerCase();

                if (articleCombinedText.contains(keywordValue.toLowerCase())) {
                    userToArticlesMap.computeIfAbsent(user, k -> new ArrayList<>()).add(articleDTO);
                    System.out.println("📌 Matched keyword '" + keywordValue + "' for user: " + user.getUsername() + " in article ID: " + articleDTO.getArticleId());
                }
            }
        }

        List<Notification> notificationList = new ArrayList<>();
        for (User user : userToArticlesMap.keySet()) {
            List<ArticleDTO> articles = userToArticlesMap.get(user);
            if (articles != null && !articles.isEmpty()) {
                StringBuilder mailBody = new StringBuilder();
                for (ArticleDTO articleDTO : articles) {
                    Notification notification = new Notification();
                    notification.setUser(user);
                    notification.setCreatedAt(LocalDateTime.now());
                    notification.setMessage(articleDTO.getTitle());
                    notificationList.add(notification);
                    mailBody.append("-------------------\n")
                            .append("Article Title : ").append(articleDTO.getTitle()).append("\n")
                            .append("Article URL   : ").append(articleDTO.getUrl()).append("\n");
                }
                System.out.println("📧 Sending email to " + user.getEmail() + " with " + articles.size() + " articles.");
                emailService.sendEmail(user.getEmail(), "Latest Articles Based on Your Preferences", mailBody.toString());
            }
        }

        System.out.println("🔔 Total notifications to save: " + notificationList.size());
//    notificationRepository.saveAll(notificationList);
        System.out.println("✅ Notification process completed.");
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
