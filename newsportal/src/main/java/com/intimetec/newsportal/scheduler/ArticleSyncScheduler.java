package com.intimetec.newsportal.scheduler;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.factory.NewsProviderFactory;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ArticleSyncScheduler {

    @Autowired
    private NewsProviderFactory newsProviderFactory;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Scheduled(cron = "0 0 */3 * * *")
    public void fetchArticle() {
        List<ArticleDTO> articleDTOList = newsProviderFactory.getBestNewsApiClient().getArticlesPeriodically();

        List<String> allCategoryNames = new ArrayList<>();
        List<Article> articleList = new ArrayList<>();

        for(ArticleDTO articleDTO : articleDTOList){
            System.out.print("All categories : "+  allCategoryNames.toString() + " ");
            allCategoryNames.addAll(articleDTO.getCategories());
        }

        Set<String> categoryNameSet = new HashSet<>(allCategoryNames);

        List<Category> existingCategoryList = categoryRepository.findByCategoryNameIn(categoryNameSet);

        Map<String,Category> categoryNameToExistingCategoryMap = new HashMap<>();

        for(Category category : existingCategoryList){
            categoryNameToExistingCategoryMap.put(category.getCategoryName(),category);
        }

        List<Category> categoryList = categoryRepository.findByCategoryNameIn(categoryNameSet);

        List<Category> nonExistingCategories = new ArrayList<>();

        for(String categoryName : categoryNameSet){
            if(!categoryNameToExistingCategoryMap.containsKey(categoryName)){
                Category category = new Category();
                category.setCategoryName(categoryName);
                nonExistingCategories.add(category);
            }
        }

        nonExistingCategories = categoryRepository.saveAll(nonExistingCategories);

        categoryList.addAll(nonExistingCategories);

        Map<String,Category> categoryNameToCategoryMap = new HashMap<>();

        for(ArticleDTO articleDTO : articleDTOList){
            List<String> categoryNames = articleDTO.getCategories();
            for(String categoryName : categoryNames){
                Article article = ArticleMapper.toEntity(articleDTO);
                Set<Category> categorySet = article.getCategories();
                categorySet.add(categoryNameToCategoryMap.get(categoryName));
                article.setCategories(categorySet);
                articleList.add(article);
            }
        }

        articleRepository.saveAll(articleList);
    }
}
