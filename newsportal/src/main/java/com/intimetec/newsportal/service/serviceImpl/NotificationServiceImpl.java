package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.*;
import com.intimetec.newsportal.exception.EntityNotFoundException;
import com.intimetec.newsportal.mapper.CategoryMapper;
import com.intimetec.newsportal.mapper.NotificationMapper;
import com.intimetec.newsportal.mapper.UserCategoryPreferenceMapper;
import com.intimetec.newsportal.model.*;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.service.CategoryService;
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

    @Autowired
    UserCategoryPreferenceMapper userCategoryPreferenceMapper;

    @Autowired
    ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryMapper categoryMapper;

    public void updateUserCategoryPreference(Long userId,CategoryNotificationPreferenceDTO categoryNotificationPreferenceDTO){
        User user = userRepository.getReferenceById(userId);
        Category category = categoryRepository.findByCategoryName(categoryNotificationPreferenceDTO.getCategoryName());

        if(category==null){
            throw new EntityNotFoundException("Category with name : " + categoryNotificationPreferenceDTO.getCategoryName());
        }
        UserCategoryPreference userCategoryPreference = userCategoryPreferenceRepository.findByUserIdAndCategoryId(userId,category.getCategoryId());
        userCategoryPreference.setNotificationsEnabled(categoryNotificationPreferenceDTO.isNotificationsEnabled());
        userCategoryPreferenceRepository.save(userCategoryPreference);
    }

    @Transactional
    @Override
    public void notifyUsersForMatchingArticles(List<ArticleDTO> articleDTOList) {
        System.out.println(" Starting user notification process for matching articles...");

        Map<Integer,List<String>> articleIdToCategoryMap = buildArticleIdToCategoryMap(articleDTOList);
        for(ArticleDTO articleDTO: articleDTOList){
            List<String> categories = articleIdToCategoryMap.get(articleDTO.getArticleId());
            if(categories != null && !categories.isEmpty()){
                articleDTO.setCategories(categories);
            }
        }

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

        System.out.println("Extracted visible category names from articles: " + categoryNames);

        List<Category> categoryList = categoryRepository.findByCategoryNameInAndIsVisibleTrue(categoryNames);
        System.out.println("Retrieved visible Category entities from DB: " + categoryList.size());

        Map<String, Category> nameToCategoryMap = new HashMap<>();
        for (Category category : categoryList) {
            if (category != null && !nameToCategoryMap.containsKey(category.getCategoryName())) {
                nameToCategoryMap.put(category.getCategoryName(), category);
            }
        }

        List<CategoryKeyword> categoryKeywordList = categoryKeywordRepository.findByCategoryIn(categoryList);
        System.out.println("Retrieved CategoryKeywords for categories: " + categoryKeywordList.size());

        List<UserCategoryKeyword> userCategoryKeywordList = userCategoryKeywordRepository.findByCategoryIn(categoryList);
        System.out.println("Retrieved UserCategoryKeywords for categories: " + userCategoryKeywordList.size());

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
                    System.out.println("Matched keyword '" + keywordValue + "' for user: " + user.getUsername() + " in article ID: " + articleDTO.getArticleId());
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
                    notification.setUserId(user.getId());
                    notification.setArticleId(articleDTO.getArticleId());
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

        System.out.println("Total notifications to save: " + notificationList.size());
        notificationRepository.saveAll(notificationList);
        System.out.println("Notification process completed.");
    }

    private Map<User, Set<Article>> mapUsersToNotifiableArticles(List<Article> articleList){
//        Map<Integer, Set<Article>> categoryIdToArticleList = new HashMap<>();
//        if (!articleList.isEmpty()) {
//            for (Article article : articleList) {
//                Set<Category> categorySet = article.getCategories();
//                if (categorySet != null && !categorySet.isEmpty()) {
//                    for (Category category : categorySet) {
//                        Integer categoryId = category.getCategoryId();
//                        categoryIdToArticleList.computeIfAbsent(categoryId, k -> new HashSet<>()).add(article);
//                    }
//                }
//            }
//        }
//
//        List<UserCategoryPreference> userCategoryPreferenceList = userCategoryPreferenceRepository.findAll();
//
//        Map<User, Set<Integer>> userIdToCategoryIdsMap = new HashMap<>();
//        for (UserCategoryPreference userCategoryPreference : userCategoryPreferenceList) {
//            User user = userCategoryPreference.getUser();
//            userIdToCategoryIdsMap.putIfAbsent(user, new HashSet<>());
//            if (userCategoryPreference.getNotificationsEnabled()) {
//                userIdToCategoryIdsMap.get(user).add(userCategoryPreference.getCategory().getCategoryId());
//            }
//        }
//
//        Set<User> userSet = userIdToCategoryIdsMap.keySet();
//        Map<User, Set<Article>> userToArticlesMap = new HashMap<>();
//
//        for (User user : userSet) {
//            Set<Article> articleSet = new HashSet<>();
//            for (Integer categoryId : userIdToCategoryIdsMap.get(user)) {
//                if (categoryIdToArticleList.containsKey(categoryId)) {
//                    articleSet.addAll(categoryIdToArticleList.get(categoryId));
//                }
//            }
//            userToArticlesMap.put(user, articleSet);
//        }
//        return userToArticlesMap;
        return null;
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        List<Notification> notificationList = notificationRepository.findByUserId(userId);
        List<NotificationDTO> notificationDTOList = NotificationMapper.toDTOList(notificationList);
        return notificationDTOList;
    }

    @Override
    public List<CategoryNotificationPreferenceDTO> getUserCategoryPreferences(Long userId) {
        System.out.println("Fetching category preferences for userId: " + userId);

        User user = userRepository.getReferenceById(userId);
        System.out.println("Got user reference: " + user.getId());
        List<UserCategoryPreference> userCategoryPreferenceList = userCategoryPreferenceRepository.findByUserId(userId);
        System.out.println("Found " + userCategoryPreferenceList.size() + " user category preferences");

        List<CategoryNotificationPreferenceDTO> categoryNotificationPreferenceDTOS = new ArrayList<>();
        List<Integer> categoryIds = new ArrayList<>();
        for (UserCategoryPreference preference : userCategoryPreferenceList) {
            if (!categoryIds.contains(preference.getCategoryId())) {
                categoryIds.add(preference.getCategoryId());
            }
        }
        System.out.println("Unique category IDs collected: " + categoryIds);
        List<Category> categoryList = categoryRepository.findAllById(categoryIds);
        System.out.println("Categories fetched from DB: " + categoryList.size());

        Map<Integer, String> idToCategoryName = new HashMap<>();
        for (Category category : categoryList) {
            if (!idToCategoryName.containsKey(category.getCategoryId())) {
                idToCategoryName.put(category.getCategoryId(), category.getCategoryName());
            }
        }
        System.out.println(" Category ID to name map: " + idToCategoryName);
        for (UserCategoryPreference preference : userCategoryPreferenceList) {
            String categoryName = idToCategoryName.get(preference.getCategoryId());
            CategoryNotificationPreferenceDTO dto = userCategoryPreferenceMapper.toDTO(preference, categoryName);
            categoryNotificationPreferenceDTOS.add(dto);
        }

        System.out.println("Final preference DTO list created with size: " + categoryNotificationPreferenceDTOS.size());

        return categoryNotificationPreferenceDTOS;
    }


    private Map<Integer, List<String>> buildArticleIdToCategoryMap(List<ArticleDTO> articleDTOList){
        List<Integer> articleIds = new ArrayList<>();
        for (ArticleDTO ArticleDTO : articleDTOList) {
            if (!articleIds.contains(ArticleDTO.getArticleId())) {
                articleIds.add(ArticleDTO.getArticleId());
            }
        }
        System.out.println("[DEBUG] Article IDs: " + articleIds);

        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findByArticleIdIn(articleIds);
        System.out.println("[DEBUG] ArticleCategory entries fetched: " + articleCategoryList.size());

        List<Integer> categoryIds = new ArrayList<>();
        for (ArticleCategory articleCategory : articleCategoryList) {
            if (!categoryIds.contains(articleCategory.getCategoryId())) {
                categoryIds.add(articleCategory.getCategoryId());
            }
        }
        System.out.println("[DEBUG] Category IDs: " + categoryIds);

        List<Category> categoryList = categoryRepository.findAllById(categoryIds);
        System.out.println("[DEBUG] Categories fetched: " + categoryList.size());

        Map<Integer, Category> idToCategoryMap = new HashMap<>();
        for (Category category : categoryList) {
            if (!idToCategoryMap.containsKey(category.getCategoryId())) {
                idToCategoryMap.put(category.getCategoryId(), category);
            }
        }

        Map<Integer, List<String>> articleIdToCategoryMap = new HashMap<>();
        for (ArticleCategory articleCategory : articleCategoryList) {
            Integer articleId = articleCategory.getArticleId();
            Integer categoryId = articleCategory.getCategoryId();

            if (!articleIdToCategoryMap.containsKey(articleId)) {
                articleIdToCategoryMap.put(articleId, new ArrayList<>());
            }
            List<String> categories = articleIdToCategoryMap.get(articleId);
            Category category = idToCategoryMap.get(categoryId);
            if (category != null && !categories.contains(category.getCategoryName())) {
                categories.add(category.getCategoryName());
            }
        }
        return articleIdToCategoryMap;
    }

    @Override
    public void createCategoryNotificationPreferences(Long userId){
        List<CategoryNotificationPreferenceDTO> categoryNotificationPreferenceDTOList = new ArrayList<>();

        User user = userRepository.getReferenceById(userId);

        List<CategoryDTO> categoryList = categoryService.getAllCategories();

        List<UserCategoryPreference> userCategoryPreferenceList = new ArrayList<>();

        for(CategoryDTO categoryDTO : categoryList){

            UserCategoryPreference userCategoryPreference = new UserCategoryPreference();
            userCategoryPreference.setUserId(userId);
            userCategoryPreference.setCategoryId(categoryDTO.getCategoryId());
            userCategoryPreference.setNotificationsEnabled(false);
            userCategoryPreferenceList.add(userCategoryPreference);
        }

        if(userCategoryPreferenceList != null && !userCategoryPreferenceList.isEmpty()){
            userCategoryPreferenceRepository.saveAll(userCategoryPreferenceList);
        }
    }
}
