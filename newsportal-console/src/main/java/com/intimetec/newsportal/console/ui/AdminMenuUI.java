package com.intimetec.newsportal.console.ui;

import com.intimetec.newsportal.console.dto.CategoryDTO;
import com.intimetec.newsportal.console.dto.ExternalServerDetailDTO;
import com.intimetec.newsportal.console.dto.ExternalServerStatusDTO;
import com.intimetec.newsportal.console.dto.ReportedArticleSummaryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminMenuUI {
    public void showAdminMenu() {
        System.out.println("\nWelcome, Admin! Please choose the options below:");
        System.out.println("1. View the list of external servers and status");
        System.out.println("2. View the external server’s details");
        System.out.println("3. Update/Edit the external server’s details");
        System.out.println("4. Add new News Category");
        System.out.println("5. View Reported Article Summary");
        System.out.println("6. Hide a Category");
        System.out.println("7. Logout");
        System.out.print("Enter your choice: ");

    }

    public void showReportedArticlesSummary(List<ReportedArticleSummaryDTO> reports) {
        if (reports.isEmpty()) {
            System.out.println("No reported articles found.");
        } else {
            System.out.println("\nReported Articles Summary:");
            Integer articleNo = 1;
            for (ReportedArticleSummaryDTO dto : reports) {
                System.out.println("📰 Article No   : " + articleNo);
                System.out.println("📌 Title        : " + dto.getTitle());
                System.out.println("📄 Description  : " + dto.getDescription());
                System.out.println("🔢 Reports Count: " + dto.getReportCount());
                System.out.println("----------------------------------------\n");
                articleNo+=1;
            }
        }
    }

    public void showCategories(List<CategoryDTO> categories) {
        System.out.println("\nAvailable Categories:");
        int i = 1;
        for (CategoryDTO category : categories) {
            System.out.println(i++ + ". " + category.getCategoryName());
        }
    }

    public void showReportedArticleActionOptions() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Hide a Reported Article");
        System.out.println("2. Go Back");
        System.out.print("Enter your choice: ");
    }
    public void showExternalServerStatuses(List<ExternalServerStatusDTO> statuses) {
        if (statuses.isEmpty()) {
            System.out.println("No servers found.");
        } else {
            System.out.println("\nList of External Servers:");
            for (ExternalServerStatusDTO dto : statuses) {
                System.out.printf("📡 %s - Status: %s - Last Accessed: %s%n",
                        dto.getSourceName(),
                        dto.getStatus(),
                        dto.getLastAccessed() != null ? dto.getLastAccessed() : "Never");
            }
        }
    }

    public void showServerDetails(List<ExternalServerDetailDTO> details) {
        System.out.println("\n📡 External Server Details:");
        for (int i = 0; i < details.size(); i++) {
            ExternalServerDetailDTO dto = details.get(i);
            System.out.printf("%d. Server Name : %s%n", i + 1, dto.getName());
            System.out.printf("   API Key     : %s%n", dto.getApiKey());
            System.out.println("--------------------------------------------------");
        }
    }
}
