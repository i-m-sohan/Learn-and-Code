package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.CreateCategoryDTO;
import com.intimetec.newsportal.mapper.CategoryMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.CategoryRepository;
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
}
