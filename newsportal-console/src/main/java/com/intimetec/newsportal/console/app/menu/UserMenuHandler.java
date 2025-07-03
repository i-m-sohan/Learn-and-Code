package com.intimetec.newsportal.console.app.menu;

import com.intimetec.newsportal.console.app.session.UserSession;
import com.intimetec.newsportal.console.dto.ArticleDTO;
import com.intimetec.newsportal.console.dto.HeadlineRequestDTO;
import com.intimetec.newsportal.console.service.ArticleService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class UserMenuHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static void start() {
        while (true) {
            System.out.println("\nWelcome! Please choose the options below:");
            System.out.println("1. Headlines");
            System.out.println("2. Saved Articles");
            System.out.println("3. Search");
            System.out.println("4. Notifications");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> handleHeadlines();
                case "2" -> System.out.println("[TODO] Show saved articles");
                case "3" -> System.out.println("[TODO] Implement search");
                case "4" -> notififetchUserNotifications(UserSession.getUserId());
                case "5" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void handleHeadlines() {
        HeadlineRequestDTO dto = new HeadlineRequestDTO();

        System.out.println("\nPlease choose the options below:");
        System.out.println("1. Today");
        System.out.println("2. Date range");
        System.out.println("3. Logout");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> {
                LocalDate today = LocalDate.now();
                dto.setStartDate(today.atStartOfDay());
                dto.setEndDate(today.atTime(LocalTime.MAX));
            }
            case "2" -> {
                System.out.print("Enter start date (YYYY-MM-DD): ");
                LocalDate start = LocalDate.parse(scanner.nextLine());
                System.out.print("Enter end date (YYYY-MM-DD): ");
                LocalDate end = LocalDate.parse(scanner.nextLine());

                dto.setStartDate(start.atStartOfDay());
                dto.setEndDate(end.atTime(LocalTime.MAX));
            }
            case "3" -> {
                System.out.println("Logging out...");
                return;
            }
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        chooseCategoryAndShowHeadlines(dto);
    }

    private static void chooseCategoryAndShowHeadlines(HeadlineRequestDTO dto) {
        System.out.println("\nPlease choose the category:");
        System.out.println("1. All");
        System.out.println("2. Business");
        System.out.println("3. Entertainment");
        System.out.println("4. Sports");
        System.out.println("5. Technology");

        String categoryInput = scanner.nextLine();
        String category;

        switch (categoryInput) {
            case "1" -> category = "All";
            case "2" -> category = "Business";
            case "3" -> category = "Entertainment";
            case "4" -> category = "Sports";
            case "5" -> category = "Technology";
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        dto.setCategory(category);
        ArticleService articleService = new ArticleService();
        List<ArticleDTO> articles = articleService.getArticlesByHeadlineRequest(dto);
        if (articles.isEmpty()) {
            System.out.println("No articles found.");
        } else {
            System.out.println("\nHEADLINES:");
            for (ArticleDTO article : articles) {
                System.out.print("Article Id: "+ article.getArticleId() +"\n" + article.getTitle() + "\n" + article.getDescription() + "\n" + "Source: "+  article.getSource() + "\n" + "URL : " + article.getUrl()+"\n");
                System.out.print("Categories : "+article.getCategories().toString()+"\n");
            }
        }
    }

}
