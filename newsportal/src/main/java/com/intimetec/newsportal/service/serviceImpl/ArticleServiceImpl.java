package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.HeadlineRequestDTO;
import com.intimetec.newsportal.exception.EntityFetchException;
import com.intimetec.newsportal.exception.CategoryException;
import com.intimetec.newsportal.exception.EntitySaveException;
import com.intimetec.newsportal.factory.NewsProviderFactory;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.ArticleCategory;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    NewsProviderFactory newsProviderFactory;

    @Override
    public List<ArticleDTO> getHeadlineArticles(HeadlineRequestDTO headlineRequestDTO){
        System.out.println(" Start Date: " + headlineRequestDTO.getStartDate());
        System.out.println(" End Date: " + headlineRequestDTO.getEndDate());

        List<Article> articleList = new ArrayList<>();

        try{
            articleList = articleRepository.findByPublishedDateBetweenAndIsVisibleTrue(
                    headlineRequestDTO.getStartDate(), headlineRequestDTO.getEndDate());
        }
        catch (Exception exception) {
            String message = "Article fetch failed for start date : " + headlineRequestDTO.getStartDate() + "End Date : "+ headlineRequestDTO.getEndDate() + "and Visible articles";
            throw new EntityFetchException(message, exception);
        }

        System.out.println(" Articles fetched from DB: " + articleList.size());

        List<Integer> articleIds = new ArrayList<>();
        for (Article article : articleList) {
            if (!articleIds.contains(article.getArticleId())) {
                articleIds.add(article.getArticleId());
            }
        }
        System.out.println(" Article IDs: " + articleIds);

        List<ArticleCategory> articleCategoryList = new ArrayList<>();
        try {
            articleCategoryList = articleCategoryRepository.findByArticleIdIn(articleIds);
        } catch (Exception exception) {
            String message = "Failed to fetch ArticleCategory list for Article IDs: " + articleIds;
            throw new EntityFetchException(message, exception);
        }

        System.out.println(" ArticleCategory entries fetched: " + articleCategoryList.size());

        List<Integer> categoryIds = new ArrayList<>();
        for (ArticleCategory articleCategory : articleCategoryList) {
            if (!categoryIds.contains(articleCategory.getCategoryId())) {
                categoryIds.add(articleCategory.getCategoryId());
            }
        }
        System.out.println(" Category IDs: " + categoryIds);

        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList = categoryRepository.findAllById(categoryIds);
        } catch (Exception exception) {
            String message = "Failed to fetch categories for category IDs: " + categoryIds;
            throw new EntityFetchException(message);
        }

        System.out.println(" Categories fetched: " + categoryList.size());

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
        System.out.println(" Article ID to Category Map: " + articleIdToCategoryMap);

        List<ArticleDTO> articleDTOList = ArticleMapper.toDTOList(articleList);
        for (ArticleDTO articleDTO : articleDTOList) {
            articleDTO.setCategories(articleIdToCategoryMap.get(articleDTO.getArticleId()));
        }

        String categoryName = headlineRequestDTO.getCategory();
        System.out.println(" Requested category: " + categoryName);

        if (categoryName == "All") {
            System.out.println(" Returning all articles (no filtering)");
            return articleDTOList;
        }

        List<ArticleDTO> finalisedArticleList = new ArrayList<>();
        for (ArticleDTO articleDTO : articleDTOList) {
            System.out.println(" Checking article: " + articleDTO.getArticleId());
            List<String> categories = articleDTO.getCategories();
            System.out.println(" Categories of article: " + categories);
            if (categories != null) {
                for (String category : categories) {
                    if (categoryName.equalsIgnoreCase(category)) {
                        finalisedArticleList.add(articleDTO);
                        break;
                    }
                }
            }
        }

        System.out.println(" Final article list size: " + finalisedArticleList.size());
        return finalisedArticleList;
    }

    @Override
    public List<Article> saveArticles(List<ArticleDTO> articleDTOList){
        System.out.println(" Saving articles...");
        List<String> allCategoryNames = extractAllCategoryNames(articleDTOList);
        System.out.println(" All Category Names: " + allCategoryNames);

        List<Category> categoryList = ensureCategoriesExist(allCategoryNames);
        for(Category category : categoryList){
            System.out.println(" Category: " + category.getCategoryName() + ", Visible: " + category.isVisible());
        }

        Map<String,Category> categoryNameToCategoryMap = buildCategoryNameToCategoryMap(categoryList);
        System.out.println(" Category Map Keys: " + categoryNameToCategoryMap.keySet());

        List<Article> articleList = constructArticleEntities(articleDTOList, categoryNameToCategoryMap);
        System.out.println(" Articles to save: " + articleList.size());
        List<Article> articles = new ArrayList<>();
        try{
            articles = articleRepository.saveAll(articleList);
        }
        catch(DataAccessException dataAccessException){
            String message = "Failed to save articles. Total articles attempted: " + articleList.size();
            throw new EntitySaveException(message, dataAccessException);
        }
        return articles;
    }

    @Override
    public List<ArticleDTO> searchArticles(String keyword) {
        System.out.println(" Searching articles for keyword: " + keyword);
        List<Article> articles = articleRepository.searchByKeyword(keyword);
        System.out.println(" Articles matched: " + articles.size());
        List<ArticleDTO> articleDTOList = ArticleMapper.toDTOList(articles);
        return articleDTOList;
    }

    private List<String> extractAllCategoryNames(List<ArticleDTO> articleDTOList){
        List<String> categoryNames = new ArrayList<>();
        for(ArticleDTO articleDTO : articleDTOList){
            for(String categoryName : articleDTO.getCategories()){
                if(categoryName != null && !categoryNames.contains(categoryName.toLowerCase())){
                    categoryNames.add(categoryName.toLowerCase());
                }
            }
        }
        return categoryNames;
    }

    private Map<String, Category> buildCategoryNameToCategoryMap(List<Category> categoryList) {
        Map<String, Category> categoryNameToCategoryMap = new HashMap<>();
        for(Category category : categoryList){
            categoryNameToCategoryMap.put(category.getCategoryName().toLowerCase(),category);
        }
        return categoryNameToCategoryMap;
    }

    private List<Category> ensureCategoriesExist(List<String> categoryNameList){
        List<Category> unsavedCategories = new ArrayList<>();

        List<Category> existingCategories = categoryRepository.findByCategoryNameIn(categoryNameList);
        Set<String> existingNames = new HashSet<>();
        for (Category category : existingCategories) {
            existingNames.add(category.getCategoryName().toLowerCase());
        }

        System.out.println(" Existing categories: " + existingNames);

        for(String categoryName : categoryNameList){
            if(existingNames!=null && !existingNames.contains(categoryName.toLowerCase())){
                Category category = new Category();
                category.setCategoryName(categoryName);
                unsavedCategories.add(category);
            }
        }

        System.out.println(" Unsaved categories: " + unsavedCategories);

        List<Category> savedCategories = categoryRepository.saveAll(unsavedCategories);
        List<Category> allCategories = new ArrayList<>();
        allCategories.addAll(savedCategories);
        allCategories.addAll(existingCategories);
        return allCategories;
    }

    private List<Article> constructArticleEntities( List<ArticleDTO> articleDTOList, Map<String,Category> categoryMap){
        List<Article> articleList = new ArrayList<>();

        for(ArticleDTO articleDTO : articleDTOList){
            Article article = ArticleMapper.toEntity(articleDTO);
            Set<Category> categorySet = article.getCategories();
            List<String> categoryNames = articleDTO.getCategories();
            Boolean isVisible = true;

            System.out.println(" Constructing article entity: " + articleDTO.getArticleId());
            for(String categoryName : categoryNames){
                Category category = categoryMap.get(categoryName);
                System.out.println(" Handling category: " + categoryName);

                if(category != null && !category.isVisible()){
                    isVisible = false;
                }
                categorySet.add(categoryMap.get(categoryName));
            }

            article.setVisible(isVisible);
            article.setCategories(categorySet);
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public void hidArticle(Integer articleId){
        System.out.println(" Hiding article ID: " + articleId);
        Article article = articleRepository.getReferenceById(articleId);
        article.setVisible(false);
        articleRepository.save(article);
    }

    public void hideArticleByCategory(Integer categoryId){
        System.out.println(" Hiding articles by category ID: " + categoryId);
        List<Article> articleList = articleRepository.findVisibleArticlesByCategoryId(categoryId);

        for(Article article : articleList){
            article.setVisible(false);
        }
        articleRepository.saveAll(articleList);
    }

    public List<ArticleDTO> fetchArticleFromExternalSources(){
        System.out.println(" Fetching articles from external sources...");
        List<NewsClient> newsClientList = newsProviderFactory.getAvailableNewsClients();
        List<ArticleDTO> fetchedArticleDTOList = new ArrayList<>();
        for(NewsClient newsClient : newsClientList){
            List<ArticleDTO> articleDTOList = newsClient.getArticlesPeriodically();
            if(articleDTOList != null){
                System.out.println(" Articles fetched from client: " + articleDTOList.size());
                fetchedArticleDTOList.addAll(articleDTOList);
            }
        }
        return fetchedArticleDTOList;
    }
}
