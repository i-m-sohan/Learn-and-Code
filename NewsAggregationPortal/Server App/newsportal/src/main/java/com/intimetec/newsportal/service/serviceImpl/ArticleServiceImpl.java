package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.service.ArticleService;
import com.intimetec.newsportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<Article> saveArticles(List<ArticleDTO> articleDTOList){

        Set<String> allCategoryNames = extractAllCategoryNames(articleDTOList);
        Map<String,Category> categoryNameToCategoryMap = buildCategoryMap(allCategoryNames);
        List<Article> articleList = constructArticleEntities(articleDTOList, categoryNameToCategoryMap);
        List<Article> savedArticlesList =  articleRepository.saveAll(articleList);

        return savedArticlesList;
    }

    private Set<String> extractAllCategoryNames(List<ArticleDTO> articleDTOList){
        Set<String> categoryNames = new HashSet<>();
        for(ArticleDTO articleDTO : articleDTOList){
            categoryNames.addAll(articleDTO.getCategories());
        }
        return categoryNames;
    }

    private Map<String, Category> buildCategoryMap(Set<String> categoryNames) {
        List<Category> categoryList = categoryService.ensureCategoriesExist(new ArrayList<>(categoryNames));
        Map<String, Category> categoryNameToCategoryMap = new HashMap<>();

        for(Category category : categoryList){
            categoryNameToCategoryMap.put(category.getCategoryName(),category);
        }

        return categoryNameToCategoryMap;
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
}
