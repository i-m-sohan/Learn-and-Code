package com.intimetec.newsportal.console.ui;

import com.intimetec.newsportal.console.dto.ArticleDTO;
import com.intimetec.newsportal.console.dto.CategoryDTO;
import com.intimetec.newsportal.console.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.console.dto.NotificationDTO;
import com.intimetec.newsportal.console.service.ArticleService;
import com.intimetec.newsportal.console.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMenuUI {

    public void showUserMenu(){
        System.out.println("\nWelcome! Please choose the options below:");
        System.out.println("1. Headlines");
        System.out.println("2. Saved Articles");
        System.out.println("3. Search");
        System.out.println("4. Notifications");
        System.out.println("5. Logout");
        System.out.print("Enter your choice: ");
    }

    public void showHeadlineDateRangeOption(){
        System.out.println("\nPlease choose the options below:");
        System.out.println("1. Today");
        System.out.println("2. Date range");
        System.out.println("3. Logout");
    }

    public void showHeadlineCategoryOption(List<CategoryDTO> categoryDTOList){
        System.out.println("\nPlease choose the category:");
        System.out.println("1. All ");
        for(int categoryIterator = 1; categoryIterator <= categoryDTOList.size() ; categoryIterator++){
            System.out.println(categoryIterator + 1 + "." + " " + categoryDTOList.get(categoryIterator-1).getCategoryName());
        }
    }

    public void showFetchedHeadlines(List<ArticleDTO> articleDTOList) {
        if (articleDTOList == null || articleDTOList.isEmpty()) {
            System.out.println("No articles found.");
            return;
        }

        System.out.println("\n========== HEADLINES ==========\n");

        int count = 1;
        for (ArticleDTO article : articleDTOList) {
            System.out.printf("Article #%d\n", count++);
            System.out.println("--------------------------------------------------");
            System.out.println("ID          : " + article.getArticleId());
            System.out.println("Title       : " + article.getTitle());
            System.out.println("Description : " + article.getDescription());
            System.out.println("Content     : " + article.getContent());
            System.out.println("Source      : " + article.getSource());
            System.out.println("URL         : " + article.getUrl());
            System.out.println("Published   : " + (article.getPublishedDate() != null ? article.getPublishedDate() : "N/A"));
            System.out.println("Categories  : " + String.join(", ", article.getCategories()));
            System.out.println("Likes       : " + article.getLikesCount());
            System.out.println("Dislikes    : " + article.getDislikesCount());
            System.out.println("Reports     : " + article.getReportCount());
            System.out.println("--------------------------------------------------\n");
        }

        System.out.println("========== END OF LIST ==========\n");
    }
    public void showPostHeadlineOptions() {
        System.out.println("\nChoose an action:");
        System.out.println("1. Save Article");
        System.out.println("2. Like Article");
        System.out.println("3. Dislike Article");
        System.out.println("4. Report Article");
        System.out.println("5. Go Back");
        System.out.print("Enter your choice: ");
    }

    public void showSavedArticlesOptions() {
        System.out.println("\nChoose an action:");
        System.out.println("1. Delete Article");
        System.out.println("2. Go Back");
        System.out.print("Enter your choice: ");
    }

    public void showNotificationMenu() {
        System.out.println();
        System.out.println("N O T I F I C A T I O N S");
        System.out.println("1. View Notifications");
        System.out.println("2. Configure Notifications");
        System.out.println("3. Back");
        System.out.println("4. Logout");
        System.out.print("Enter your choice: ");
    }

    public void showUserNotifications(List<NotificationDTO> notifications) {
        System.out.println("\n📬 Your Notifications:");
        System.out.println("-----------------------------------------------------------");

        if (notifications.isEmpty()) {
            System.out.println("No notifications available.");
            return;
        }

        int count = 1;
        for (NotificationDTO notification : notifications) {
            System.out.println("🔔 Notification " + count++);
            System.out.println("-----------------------------------------------------------");
            System.out.println("📄 Message     : " + notification.getMessage());
            System.out.println("📰 Article ID  : " + (notification.getArticleId() != null ? notification.getArticleId() : "N/A"));
            System.out.println("🕒 Received At : " + notification.getCreatedAt());
            System.out.println("-----------------------------------------------------------\n");
        }
    }

    public void showCategoryPreferences(List<CategoryNotificationPreferenceDTO> preferences) {
        System.out.println("\n🛠️ Your Category Notification Preferences:");
        if (preferences.isEmpty()) {
            System.out.println("No preferences configured.");
            return;
        }
        int i = 1;
        for (CategoryNotificationPreferenceDTO pref : preferences) {
            System.out.printf("%d. [%s] - Notifications %s%n",
                    i++,
                    pref.getCategoryName(),
                    pref.isNotificationsEnabled() ? "✅ Enabled" : "❌ Disabled"
            );
        }
        System.out.println();
    }

    public void showConfigureNotificationOptions() {
        System.out.println("\nChoose an action:");
        System.out.println("1. Update Notification Preference");
        System.out.println("2. Add Keywords to Category");
        System.out.println("3. Back");
        System.out.print("Enter your choice: ");
    }

    public void showNotificationPreferenceOptions() {
        System.out.println("What would you like to do?");
        System.out.println("1. Enable/Disable Category Notification");
        System.out.println("2. Add Keywords to Category");
        System.out.println("3. Remove Keywords from Category");
        System.out.println("4. Back");
        System.out.print("Enter your choice: ");
    }

    public void showServerDetailActionOptions() {
        System.out.println("What would you like to do?");
        System.out.println("1. Update API key");
        System.out.println("2. Go Back");
        System.out.print("Enter your choice: ");
    }
}
