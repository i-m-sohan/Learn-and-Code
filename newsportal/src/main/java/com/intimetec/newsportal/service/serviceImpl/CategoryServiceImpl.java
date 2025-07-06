package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.mapper.CategoryMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.model.CategoryKeyword;
import com.intimetec.newsportal.model.Keyword;
import com.intimetec.newsportal.repository.CategoryKeywordRepository;
import com.intimetec.newsportal.repository.CategoryRepository;
import com.intimetec.newsportal.repository.KeywordRepository;
import com.intimetec.newsportal.service.ArticleService;
import com.intimetec.newsportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    CategoryKeywordRepository categoryKeywordRepository;

    @Override
    public List<Category> ensureCategoriesExist(List<String> categoryNameList){
        Set<String> categoryNameSet = new HashSet<>(categoryNameList);
        List<Category> categoryList = categoryRepository.findByCategoryNameIn(categoryNameSet);

        Map<String,Category> categoryNameToCategoryMap = new HashMap<>();
        for(Category category : categoryList){
            categoryNameToCategoryMap.put(category.getCategoryName(),category);
        }

        for(String categoryName : categoryNameSet){
            if(!categoryNameToCategoryMap.containsKey(categoryName)){
                Category category = new Category(categoryName);
                categoryList.add(category);
            }
        }
        List<Category> allCategoryList =  categoryRepository.saveAll(categoryList);
        return allCategoryList;
//        Set<String> categoryNameSet = new HashSet<>(categoryNameList);
//        List<Category> existingCategoryList = categoryRepository.findByCategoryNameIn(categoryNameSet);
//
//        Map<String,Category> categoryNameToCategoryMap = new HashMap<>();
//        for(Category category : existingCategoryList){
//            categoryNameToCategoryMap.put(category.getCategoryName(),category);
//        }
//
//        List<Category> categoriesToCreateList = new ArrayList<>();
//        for(String categoryName : categoryNameSet){
//            if(!categoryNameToCategoryMap.containsKey(categoryName)){
//                Category category = new Category();
//                category.setCategoryName(categoryName);
//                categoriesToCreateList.add(category);
//            }
//        }
//
//        List<Category> createdCategoryList = categoryRepository.saveAll(categoriesToCreateList);
//
//        List<Category> allCategoriesList = new ArrayList<>(existingCategoryList);
//        allCategoriesList.addAll(categoriesToCreateList);
//        return allCategoriesList;
    }

    @Override
    public void createCategory(CreateCategoryDTO createCategoryDTO){
        Category category = categoryMapper.toEntity(createCategoryDTO);
        categoryRepository.save(category);
    }

    public void hideCategory(Integer categoryId){
        Category category  = categoryRepository.getReferenceById(categoryId);
        category.setVisible(false);
        articleService.hideArticleByCategory(categoryId);
    }

    public void defineArticlesCategory(List<ArticleDTO> articleDTOList){
        List<CategoryKeyword> categoryKeywordList = categoryKeywordRepository.findAll();

        for(ArticleDTO articleDTO : articleDTOList){
            if(articleDTO.getCategories() == null || articleDTO.getCategories().isEmpty()){
                defineArticleCategory(articleDTO,categoryKeywordList);
            }
        }
    }

    private void defineArticleCategory(ArticleDTO articleDTO,List<CategoryKeyword> categoryKeywordList){
        String title = articleDTO.getTitle() != null ? articleDTO.getTitle() : "";
        String description = articleDTO.getDescription() != null ? articleDTO.getDescription() : "";
        String content = articleDTO.getContent() != null ? articleDTO.getContent() : "";
        String articleCombinedText = (title + " " + description + " " + content).toLowerCase();

        Map<Category, Integer> categoryMatchCountMap = new HashMap<>();
        for(CategoryKeyword categoryKeyword : categoryKeywordList){
            Keyword keyword = categoryKeyword.getKeyword();
            Category category = categoryKeyword.getCategory();

            if(articleCombinedText.contains(keyword.getKeyword())){
                categoryMatchCountMap.put(category,categoryMatchCountMap.getOrDefault(category,0)+1);
            }
        }

        if(categoryMatchCountMap.isEmpty()){
            articleDTO.setCategories(List.of("General"));
            return;
        }

        int maxMatch = Collections.max(categoryMatchCountMap.values());
        int threshold = maxMatch / 2;

        List<String> matchedCategoryNames = new ArrayList<>();
        for (Map.Entry<Category, Integer> categoryToCountPair : categoryMatchCountMap.entrySet()) {
            Category category = categoryToCountPair.getKey();
            Integer matchCount = categoryToCountPair.getValue();
            if (matchCount >= threshold) {
                String categoryName = category.getCategoryName();
                if (!matchedCategoryNames.contains(categoryName)) {
                    matchedCategoryNames.add(categoryName);
                }
            }
        }
        articleDTO.setCategories(matchedCategoryNames);
    }
}
