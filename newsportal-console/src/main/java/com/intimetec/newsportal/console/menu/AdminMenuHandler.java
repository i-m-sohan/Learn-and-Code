package com.intimetec.newsportal.console.menu;

import com.intimetec.newsportal.console.dto.*;
import com.intimetec.newsportal.console.exception.ArticleExcpetion;
import com.intimetec.newsportal.console.exception.CategoryException;
import com.intimetec.newsportal.console.exception.ExternalServerException;
import com.intimetec.newsportal.console.service.ArticleService;
import com.intimetec.newsportal.console.service.CategoryService;
import com.intimetec.newsportal.console.service.ExternalServerService;
import com.intimetec.newsportal.console.ui.AdminMenuUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class AdminMenuHandler {

    private Scanner scanner = new Scanner(System.in);
    @Autowired
    private ExternalServerService externalServerService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AdminMenuUI adminMenuUI;
    @Autowired
    private ArticleService articleService;

    public void start() {
        while (true) {
            adminMenuUI.showAdminMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> showServerStatuses();
                case "2" -> showServerDetails();
                case "3" -> updateServerDetails();  // corrected method reference
                case "4" -> addNewCategory();
                case "5" -> showReportedArticleSummary();
                case "6" -> hideCategory();
                case "7" -> {
                    System.out.println("Logging Out.....");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showServerStatuses() {
        try{
            List<ExternalServerStatusDTO> statuses = externalServerService.getAllServerStatuses();
            adminMenuUI.showExternalServerStatuses(statuses);
        }
        catch(ExternalServerException externalServerException){
            System.out.println(externalServerException.getMessage());
        }
    }

    private void showServerDetails() {
        try{
            List<ExternalServerDetailDTO> externalServerDetailDTOList = externalServerService.getAllServerDetails();
            adminMenuUI.showServerDetails(externalServerDetailDTOList);
        }
        catch(ExternalServerException externalServerException){
            System.out.println(externalServerException.getMessage());
        }
    }

    private void updateServerDetails() {
        try{
            List<ExternalServerDetailDTO> serverDetails = externalServerService.getAllServerDetails();
            adminMenuUI.showServerDetails(serverDetails);
            while (true) {
                    System.out.print("Enter the serial number of the server to update: (0 to Go Back) ");
                    int serialNumber = Integer.parseInt(scanner.nextLine().trim());

                    if (serialNumber == 0) {
                        System.out.println("Going Back......");
                        return;
                    }

                    ExternalServerDetailDTO selectedServer = serverDetails.get(serialNumber - 1);
                    Long serverId = selectedServer.getExternalServerId();

                    System.out.print("Enter the new API Key: ");
                    String newApiKey = scanner.nextLine();

                    ExternalServerUpdateDTO dto = new ExternalServerUpdateDTO();
                    dto.setServerID(serverId);
                    dto.setApiKey(newApiKey);

                    externalServerService.updateServerApiKey(dto);
                    System.out.println("API key updated successfully for server: " + selectedServer.getName());
            }
        }
        catch(ExternalServerException externalServerException){
            System.out.println(externalServerException.getMessage());
        }
        catch(IndexOutOfBoundsException indexOutOfBoundsException){
            System.out.println("Enter Valid No - Server Serial no does not exist");
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
        catch (Exception e) {
            System.out.println("Unable to update : Error Occured");
        }

    }


    private void addNewCategory() {
        System.out.print("Enter new category name: ");
        String categoryName = scanner.nextLine();

        CreateCategoryDTO createCategoryDTO = new CreateCategoryDTO();
        createCategoryDTO.setCategoryName(categoryName);
        try {
            categoryService.createCategory(createCategoryDTO);
        }
        catch(CategoryException categoryException){
            System.out.println(categoryException.getMessage());
        }
    }

    private void showReportedArticleSummary() {
        try{
            List<ReportedArticleSummaryDTO> reports = articleService.getReportedArticlesSummary();
            adminMenuUI.showReportedArticlesSummary(reports);

            if (!reports.isEmpty()) {
                while (true) {
                    adminMenuUI.showReportedArticleActionOptions();
                    String input = scanner.nextLine().trim();

                    switch (input) {
                        case "1" -> {
                            hideReportedArticle(reports);
                            return;
                        }
                        case "2" -> {
                            System.out.println("Going back...");
                            return;
                        }
                        default -> System.out.println("Invalid choice. Please enter 1 or 2.");
                    }
                }
            }
        }
        catch(ArticleExcpetion articleExcpetion){
            System.out.println(articleExcpetion.getMessage());
        }
    }


    private void hideCategory() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        adminMenuUI.showCategories(categories); // display numbered list

        System.out.print("Enter category number to hide: (or " + 0 + " to Go Back)");
        try {
            int selectedIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if(selectedIndex==-1){
                System.out.println("Going Back.......");
                return ;
            }
            System.out.println("Category Name : " + categories.get(selectedIndex).getCategoryName());
            int categoryId = categories.get(selectedIndex).getCategoryId();
            categoryService.hideCategory(categoryId);
        }
        catch(CategoryException categoryException){
            System.out.println(categoryException.getMessage());
        }
        catch(IndexOutOfBoundsException indexOutOfBoundsException){
            System.out.println("Invalid No - Mention Category No from above list!");
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void hideReportedArticle(List<ReportedArticleSummaryDTO> reports) {
        try {
            System.out.print("Enter the Article No to hide (or 0 to cancel): ");
            int articleNo = Integer.parseInt(scanner.nextLine());

            if (articleNo == 0) {
                System.out.println("Cancelled. Going back...");
                return;
            }

            int articleId = reports.get(articleNo-1).getArticleId();
            articleService.hideArticle(articleId);
        }
        catch (ArticleExcpetion articleExcpetion){
            System.out.println(articleExcpetion.getMessage());
        }
        catch(IndexOutOfBoundsException indexOutOfBoundsException){
            System.out.println("Aricle Serial No does not exist");
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric Article ID.");
        }
    }

}
