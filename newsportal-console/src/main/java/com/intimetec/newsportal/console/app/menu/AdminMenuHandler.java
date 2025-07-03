package com.intimetec.newsportal.console.app.menu;

import com.intimetec.newsportal.console.dto.CreateCategoryDTO;
import com.intimetec.newsportal.console.dto.ExternalServerDetailDTO;
import com.intimetec.newsportal.console.dto.ExternalServerStatusDTO;
import com.intimetec.newsportal.console.dto.ExternalServerUpdateDTO;
import com.intimetec.newsportal.console.service.CategoryService;
import com.intimetec.newsportal.console.service.ExternalServerService;

import java.util.List;
import java.util.Scanner;

public class AdminMenuHandler {

    private final static Scanner scanner = new Scanner(System.in);
    private final static ExternalServerService externalServerService = new ExternalServerService();
    private static final CategoryService categoryService = new CategoryService();

    public static void start() {
        while (true) {
            System.out.println("\nWelcome, Admin! Please choose the options below:");
            System.out.println("1. View the list of external servers and status");
            System.out.println("2. View the external server’s details");
            System.out.println("3. Update/Edit the external server’s details");
            System.out.println("4. Add new News Category");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showServerStatuses();
                case "2" -> showServerDetails();
                case "3" -> updateServerApiKey();
                case "4" -> addNewCategory();
                case "5" -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void showServerStatuses() {
        List<ExternalServerStatusDTO> statuses = externalServerService.getAllServerStatuses();
        if (statuses.isEmpty()) {
            System.out.println("No servers found.");
        } else {
            System.out.println("\nList of external servers::");
            for (ExternalServerStatusDTO externalServerStatusDTO : statuses) {
                System.out.print(externalServerStatusDTO.getSourceName()+ " - " + externalServerStatusDTO.getStatus()+ " - " + "last acesssed: "+ externalServerStatusDTO.getLastAccessed() + "\n");
            }
        }
    }
    private static void showServerDetails() {
        List<ExternalServerDetailDTO> details = externalServerService.getAllServerDetails();
        if (details.isEmpty()) {
            System.out.println("No server details found.");
        } else {
            System.out.println("\nList of external server details:");
            for (ExternalServerDetailDTO externalServerDetailDTO : details) {
                System.out.print(externalServerDetailDTO.getName()+ " - " + externalServerDetailDTO.getApiKey() + "\n");
            }
        }
    }
    private static void updateServerApiKey() {
        System.out.print("Enter the Server ID: ");
        Long serverId = Long.parseLong(scanner.nextLine());

        System.out.print("Enter the new API Key: ");
        String newApiKey = scanner.nextLine();

        ExternalServerUpdateDTO dto = new ExternalServerUpdateDTO();
        dto.setServerID(serverId);
        dto.setApiKey(newApiKey);

        externalServerService.updateServerApiKey(dto);
    }

    private static void addNewCategory() {
        System.out.print("Enter new category name: ");
        String categoryName = scanner.nextLine();

        CreateCategoryDTO createCategoryDTO = new CreateCategoryDTO();
        createCategoryDTO.setCategoryName(categoryName);

        categoryService.createCategory(createCategoryDTO);
    }

}
