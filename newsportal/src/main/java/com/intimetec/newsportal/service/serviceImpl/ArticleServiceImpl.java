package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.HeadlineRequestDTO;
import com.intimetec.newsportal.factory.NewsProviderFactory;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.repository.CategoryRepository;
import com.intimetec.newsportal.repository.ReactionRepository;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    NewsProviderFactory newsProviderFactory;

    @Override
    public List<ArticleDTO> getHeadlineArticles(HeadlineRequestDTO headlineRequestDTO){
        List<Article> articleList =  articleRepository.findByPublishedDateBetween(headlineRequestDTO.getStartDate(),headlineRequestDTO.getEndDate());

        List<Article> finalisedArticleList = new ArrayList<>();
        String categoryName = headlineRequestDTO.getCategory();

        if(!"All".equalsIgnoreCase(categoryName)){
            if(articleList!=null){
                for(Article article : articleList){
                    Set<Category> categorySet = article.getCategories();
                    for(Category category : categorySet){
                        if(category.getCategoryName().equalsIgnoreCase(categoryName)){
                            break;
                        }
                    }
                    finalisedArticleList.add(article);
                }
            }
        }

        List<ArticleDTO> articleDTOList = ArticleMapper.toDTOList(finalisedArticleList);
        return articleDTOList;
    }

    @Override
    public void saveArticles(List<ArticleDTO> articleDTOList){

        List<String> allCategoryNames = extractAllCategoryNames(articleDTOList);
        List<Category> categoryList = ensureCategoriesExist(allCategoryNames);

        Map<String,Category> categoryNameToCategoryMap = buildCategoryNameToCategoryMap(categoryList);
        List<Article> articleList = constructArticleEntities(articleDTOList, categoryNameToCategoryMap);
        articleRepository.saveAll(articleList);
    }

    @Override
    public List<ArticleDTO> searchArticles(String keyword) {
        List<Article> articles = articleRepository.searchByKeyword(keyword);
        List<ArticleDTO> articleDTOList = ArticleMapper.toDTOList(articles);
        return articleDTOList;
    }

    private List<String> extractAllCategoryNames(List<ArticleDTO> articleDTOList){
        List<String> categoryNames = new ArrayList<>();
        for(ArticleDTO articleDTO : articleDTOList){
            for(String categoryName : articleDTO.getCategories()){
                if(!categoryNames.contains(categoryName)){
                    categoryNames.add(categoryName);
                }
            }
        }
        return categoryNames;
    }

    private Map<String, Category> buildCategoryNameToCategoryMap(List<Category> categoryList) {

        Map<String, Category> categoryNameToCategoryMap = new HashMap<>();
        for(Category category : categoryList){
            categoryNameToCategoryMap.put(category.getCategoryName(),category);
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

        System.out.println("##### Existing categories :  "+existingNames);

        for(String categoryName : categoryNameList){
            if(existingNames!=null && !existingNames.contains(categoryName.toLowerCase())){
                Category category = new Category();
                category.setCategoryName(categoryName);
                unsavedCategories.add(category);
            }
        }

        System.out.println(unsavedCategories);

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
            for(String categoryName : categoryNames){
                categorySet.add(categoryMap.get(categoryName));
            }
            article.setCategories(categorySet);
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public void hidArticle(Integer articleId){
        Article article = articleRepository.getReferenceById(articleId);
        article.setVisible(false);
        articleRepository.save(article);
    }

    public void hideArticleByCategory(Integer categoryId){
        List<Article> articleList = articleRepository.findVisibleArticlesByCategoryId(categoryId);

        for(Article article : articleList){
            article.setVisible(false);
        }
        articleRepository.saveAll(articleList);
    }

    public List<ArticleDTO> fetchArticleFromExternalSources(){

        List<NewsClient> newsClientList = newsProviderFactory.getAvailableNewsClients();
        List<ArticleDTO> fetchedArticleDTOList = new ArrayList<>();
        for(NewsClient newsClient : newsClientList){
            List<ArticleDTO> articleDTOList = newsClient.getArticlesPeriodically();
            if(articleDTOList != null){
                fetchedArticleDTOList.addAll(articleDTOList);
            }
        }
        return fetchedArticleDTOList;
    }
}
