package com.intimetec.newsportal.console.menu;

import com.intimetec.newsportal.console.app.session.UserSession;
import com.intimetec.newsportal.console.dto.*;
import com.intimetec.newsportal.console.enums.ReactionType;
import com.intimetec.newsportal.console.exception.ArticleExcpetion;
import com.intimetec.newsportal.console.exception.CategoryException;
import com.intimetec.newsportal.console.service.ArticleService;
import com.intimetec.newsportal.console.service.CategoryService;
import com.intimetec.newsportal.console.service.NotificationService;
import com.intimetec.newsportal.console.ui.UserMenuUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class UserMenuHandler {
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserMenuUI userMenuUI;

    @Autowired
    ArticleService articleService;

    @Autowired
    NotificationService notificationService;

    public void start() {
        while (true) {
            if(!UserSession.isSessionRunning()){
                return;
            }
            userMenuUI.showUserMenu();

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> handleHeadlineRequest();
                case "2" -> showSavedArticles();
                case "3" -> handleSearch();
                case "4" -> showNotificationMenu();
                case "5" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void handleSearch() {
        System.out.print("Enter keyword to search articles: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("Keyword cannot be empty.");
            return;
        }

        List<ArticleDTO> results = articleService.searchArticles(keyword);
        userMenuUI.showFetchedHeadlines(results);
        if (!results.isEmpty()) {
            handlePostHeadlineActions(results);
        }
    }

    private  void handleHeadlineRequest() {
        HeadlineRequestDTO headlineRequestDTO = new HeadlineRequestDTO();
        userMenuUI.showHeadlineDateRangeOption();
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> {
                LocalDate today = LocalDate.now();
                headlineRequestDTO.setStartDate(today.atStartOfDay());
                headlineRequestDTO.setEndDate(today.atTime(LocalTime.MAX));
                chooseCategoryAndShowHeadlines(headlineRequestDTO);
            }
            case "2" -> {
                try {
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String startInput = scanner.nextLine();
                    LocalDate start = LocalDate.parse(startInput);

                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String endInput = scanner.nextLine();
                    LocalDate end = LocalDate.parse(endInput);

                    if (end.isBefore(start)) {
                        System.out.println("End date cannot be before start date.");
                        handleHeadlineRequest();
                    }
                    else{
                        headlineRequestDTO.setStartDate(start.atStartOfDay());
                        headlineRequestDTO.setEndDate(end.atTime(LocalTime.MAX));
                        chooseCategoryAndShowHeadlines(headlineRequestDTO);
                    }
                }
                catch (Exception e) {
                    System.out.println("Invalid Date Entered --- Going Back to Previous Menu");
                    return;
                }
            }
            case "3" -> {
                UserSession.clear();
                System.out.println("Logging out...");
                return;
            }
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }
    }

    private void chooseCategoryAndShowHeadlines(HeadlineRequestDTO headlineRequestDTO) {

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        try {
            categoryDTOList = categoryService.getAllCategories();
        }
        catch (CategoryException categoryException){
            System.out.println(categoryException.getMessage());
            return;
        }

        userMenuUI.showHeadlineCategoryOption(categoryDTOList);
        String categoryInput = scanner.nextLine();
        String selectedCategory = "";
        switch (categoryInput) {
            case "1" -> selectedCategory = "All";

            default -> {
                try {
                    int choice = Integer.parseInt(categoryInput) - 2;
                    if (choice >= 0 && choice < categoryDTOList.size()) {
                        selectedCategory = categoryDTOList.get(choice).getCategoryName();
                    } else {
                        System.out.println("Invalid choice.");
                        return;
                    }
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException){
                    System.out.println("Give Valid Category No.");
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
        }
        try{
            headlineRequestDTO.setCategory(selectedCategory);
            List<ArticleDTO> articleDTOS = articleService.getHeadlines(headlineRequestDTO);
            userMenuUI.showFetchedHeadlines(articleDTOS);
            handlePostHeadlineActions(articleDTOS);
        }
        catch(ArticleExcpetion articleExcpetion){
            System.out.println(articleExcpetion.getMessage());
        }
    }

    private void handlePostHeadlineActions(List<ArticleDTO> articles) {
        if(articles.isEmpty()){
            return;
        }
        while (true) {
            userMenuUI.showPostHeadlineOptions();
            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Enter Article ID to save: ");
                        int articleNo = Integer.parseInt(scanner.nextLine());
                        int articleId = articles.get(articleNo - 1).getArticleId();
                        articleService.saveArticle(articleId);
                    }
                    case "2" -> {
                        System.out.print("Enter Article ID to like: ");
                        int articleNo = Integer.parseInt(scanner.nextLine());
                        int articleId = articles.get(articleNo - 1).getArticleId();
                        articleService.reactToArticle(articleId, ReactionType.LIKE);
                    }
                    case "3" -> {
                        System.out.print("Enter Article ID to Dislike: ");
                        int articleNo = Integer.parseInt(scanner.nextLine());
                        int articleId = articles.get(articleNo - 1).getArticleId();
                        articleService.reactToArticle(articleId, ReactionType.DISLIKE);
                    }
                    case "4" -> {
                        System.out.print("Enter Article ID to report: ");
                        int articleNo = Integer.parseInt(scanner.nextLine());
                        int articleId = articles.get(articleNo - 1).getArticleId();
                        System.out.print("Enter reason for reporting: ");
                        String reason = scanner.nextLine();
                        articleService.reportArticle(articleId, reason);

                    }
                    case "5" -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice. Enter Again...");
                }
            }
            catch(ArticleExcpetion articleExcpetion){
                System.out.println(articleExcpetion.getMessage());
            }
            catch(IndexOutOfBoundsException indexOutOfBoundsException) {
                System.out.println("Enter Valid Article ID");
            } catch (NumberFormatException e) {
                System.out.println("Invalid article ID. Please enter a number.");
            }

        }
    }

    private void showNotificationMenu() {
        while (true) {
            userMenuUI.showNotificationMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    List<NotificationDTO> notificationDTOS = notificationService.getUserNotifications();
                    userMenuUI.showUserNotifications(notificationDTOS);
                }
                case "2" -> {
                    List<CategoryNotificationPreferenceDTO> preferences = notificationService.getCategoryPreferences();
                    userMenuUI.showCategoryPreferences(preferences);
                    handleNotificationPreferenceUpdate(preferences);
                }
                case "3" -> {
                    return;
                }
                case "4" -> {
                    UserSession.clear();
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void handleNotificationPreferenceUpdate(List<CategoryNotificationPreferenceDTO> preferences) {
        while (true) {
            userMenuUI.showNotificationPreferenceOptions();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    try{
                        configureCategoryNotification(preferences);
                    }
                    catch(CategoryException categoryException){
                       System.out.println(categoryException.getMessage());
                    }
                }
                case "2" -> saveCategoryKeywords(); //addKeywordsToCategory();     // you can implement this later
                case "3" -> removeCategoryKeywords(); //removeKeywordsFromCategory(); // you can implement this later
                case "4" -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void saveCategoryKeywords() {
        try {
            System.out.print("Enter category name: ");
            String categoryName = scanner.nextLine();

            System.out.print("Enter comma-separated keywords to add: ");
            String input = scanner.nextLine();
            List<String> keywords = List.of(input.split("\\s*,\\s*"));
            CategoryKeywordDTO categoryKeywordDTO = new CategoryKeywordDTO();
            categoryKeywordDTO.setCategoryName(categoryName);
            categoryKeywordDTO.setKeywords(keywords);
            notificationService.saveCategoryKeywords(categoryKeywordDTO);
        }
        catch (CategoryException categoryException){
            System.out.println(categoryException.getMessage());
        }
        catch (Exception e) {
            System.out.println(" Error while updating category keywords: " + e.getMessage());
        }
    }

    private void removeCategoryKeywords() {
        try {
            System.out.print("Enter category name: ");
            String categoryName = scanner.nextLine();

            System.out.print("Enter comma-separated keywords to remove: ");
            String input = scanner.nextLine();
            List<String> keywords = List.of(input.split("\\s*,\\s*"));
            CategoryKeywordDTO categoryKeywordDTO = new CategoryKeywordDTO();
            categoryKeywordDTO.setCategoryName(categoryName);
            categoryKeywordDTO.setKeywords(keywords);
            notificationService.removeCategoryKeywords(categoryKeywordDTO);
        }
        catch(CategoryException categoryException){
            System.out.println(categoryException.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error while updating category keywords: " + e.getMessage());
        }
    }

    private void configureCategoryNotification(List<CategoryNotificationPreferenceDTO> preferences) {
        try {
            System.out.print("Enter category name to update preference: ");
            String categoryName = scanner.nextLine();

            System.out.println("Choose preference:\n1. Enable Notifications\n2. Disable Notifications");
            int choice = Integer.parseInt(scanner.nextLine());

            boolean isEnabled;

            if (choice == 1) {
                isEnabled = true;
            } else if (choice == 2) {
                isEnabled = false;
            } else {
                throw new NumberFormatException("Invalid choice: " + choice + ". Only 1 (Enable) or 2 (Disable) are allowed.");
            }

            notificationService.updateCategoryPreference(categoryName, isEnabled);
        }
        catch(CategoryException categoryException){
            System.out.println(categoryException.getMessage());
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
        catch (Exception e) {
            System.out.println(" Error while updating preference: " + e.getMessage());
        }
    }

    private void showSavedArticles(){
        List<ArticleDTO> savedArticles = articleService.getSavedArticles();
        if (savedArticles.isEmpty()) {
            System.out.println("No saved articles found.");
            return;
        } else {
            userMenuUI.showFetchedHeadlines(savedArticles);
        }

        try{
            while (true) {
                userMenuUI.showSavedArticlesOptions();
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        System.out.print("Enter Article ID to delete from saved: ");
                        try {
                            int articleNo = Integer.parseInt(scanner.nextLine());
                            int articleId = savedArticles.get(articleNo - 1).getArticleId();
                            articleService.removeSavedArticle(articleId);
                        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                            System.out.println("Article Not present for your input");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid article ID. Please enter a number.");
                        }

                    }
                    case "2" -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }
        catch (ArticleExcpetion articleExcpetion){
            System.out.println(articleExcpetion.getMessage());
        }
    }
}
