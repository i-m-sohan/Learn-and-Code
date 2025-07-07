package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.CategoryDTO;
import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.exception.EntityFetchException;
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
    public List<CategoryDTO> getAllCategories(){
        List<Category> categoryList  = new ArrayList<>();
        try {
            categoryList =  categoryRepository.findByIsVisibleTrue();
        }
        catch(Exception exception){
            throw new EntityFetchException("Error occured while fetching all categories");
        }
        List<CategoryDTO> categoryDTOS = categoryMapper.toDTOList(categoryList);
        return categoryDTOS;
    }

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
        System.out.println("Total CategoryKeyword entries: " + categoryKeywordList.size());

        for (ArticleDTO articleDTO : articleDTOList) {
            System.out.println("\nProcessing Article: " + articleDTO.getArticleId() + " | Title: " + articleDTO.getTitle());

            if (articleDTO.getCategories() == null || articleDTO.getCategories().isEmpty()) {
                System.out.println("No categories found. Running category assignment...");
                defineArticleCategory(articleDTO, categoryKeywordList);
            } else {
                System.out.println("Categories already present: " + articleDTO.getCategories());
            }
        }
    }

    private void defineArticleCategory(ArticleDTO articleDTO, List<CategoryKeyword> categoryKeywordList) {
        String title = articleDTO.getTitle() != null ? articleDTO.getTitle() : "";
        String description = articleDTO.getDescription() != null ? articleDTO.getDescription() : "";
        String content = articleDTO.getContent() != null ? articleDTO.getContent() : "";

        String articleCombinedText = (title + " " + description + " " + content).toLowerCase();

        Map<Category, Integer> categoryMatchCountMap = new HashMap<>();
        for (CategoryKeyword categoryKeyword : categoryKeywordList) {
            Keyword keyword = categoryKeyword.getKeyword();
            Category category = categoryKeyword.getCategory();

            if (articleCombinedText.contains(keyword.getKeyword().toLowerCase())) {
                int currentCount = categoryMatchCountMap.getOrDefault(category, 0) + 1;
                categoryMatchCountMap.put(category, currentCount);
                System.out.println("Matched keyword: '" + keyword.getKeyword() + "' for category: " + category.getCategoryName());
            }
        }

        if (categoryMatchCountMap.isEmpty()) {
            System.out.println("No matching keywords found. Assigning category: general");
            articleDTO.setCategories(List.of("general"));
            return;
        }

        int maxMatch = Collections.max(categoryMatchCountMap.values());
        int threshold = maxMatch / 2;
        System.out.println("Max match count: " + maxMatch + ", Threshold: " + threshold);

        List<String> matchedCategoryNames = new ArrayList<>();
        for (Map.Entry<Category, Integer> entry : categoryMatchCountMap.entrySet()) {
            Category category = entry.getKey();
            Integer count = entry.getValue();

            if (count >= threshold) {
                String categoryName = category.getCategoryName();
                if (!matchedCategoryNames.contains(categoryName)) {
                    matchedCategoryNames.add(categoryName);
                    System.out.println("Added category '" + categoryName + "' with match count: " + count);
                }
            } else {
                System.out.println("Category '" + category.getCategoryName() + "' ignored (count: " + count + ")");
            }
        }

        if(matchedCategoryNames!=null && !matchedCategoryNames.isEmpty()){
            articleDTO.setCategories(matchedCategoryNames);
        }
        else{
            articleDTO.setCategories(List.of("general"));
        }
        System.out.println("Final assigned categories: " + matchedCategoryNames);
    }

}
