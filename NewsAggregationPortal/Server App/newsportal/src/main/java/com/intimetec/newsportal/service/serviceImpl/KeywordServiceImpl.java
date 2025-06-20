package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.model.CategoryKeyword;
import com.intimetec.newsportal.model.Keyword;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.repository.CategoryKeywordRepository;
import com.intimetec.newsportal.repository.CategoryRepository;
import com.intimetec.newsportal.repository.KeywordRepository;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.CategoryService;
import com.intimetec.newsportal.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    CategoryKeywordRepository categoryKeywordRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    KeywordRepository keywordRepository;

    @Autowired
    CategoryService categoryService;

    @Override
    public void saveUserCategoryKeyword(CategoryKeywordDTO categoryKeywordDTO) {
//        User user = userRepository.getReferenceById(categoryKeywordDTO.getUserId());
//        Category category = categoryRepository.findByCategoryName(categoryKeywordDTO.getCategoryName());
//
//        List<String> keywordNameList = categoryKeywordDTO.getKeywords();
//
//        List<Keyword> keywordEntityList = new ArrayList<>();
//        for(String keywordName : keywordNameList){
//            Keyword keyword = new Keyword();
//            keyword.setKeyword(keywordName);
//            keywordEntityList.add(keyword);
//        }
//
//        List<Keyword> createdKeywordList =  keywordRepository.saveAll(keywordEntityList);
    }

    public void addUCategoryKeyword(@RequestBody CategoryKeywordDTO categoryKeywordDTO){
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add(categoryKeywordDTO.getCategoryName());

        List<Category> categoryList = categoryService.ensureCategoriesExist(categoryNames);

        List<Keyword> keywordList = new ArrayList<>();

        List<String> keywordNameList = categoryKeywordDTO.getKeywords();

        List<Keyword> keywordEntityList = new ArrayList<>();

        for(String keywordName : keywordNameList){
            Keyword keyword = new Keyword();
            keyword.setKeyword(keywordName);
            keywordEntityList.add(keyword);
        }
        List<Keyword> createdKeywordList = keywordRepository.saveAll(keywordEntityList);

        List<CategoryKeyword> categoryKeywordList = new ArrayList<>();

        for(Keyword keyword : createdKeywordList){
            CategoryKeyword categoryKeyword = new CategoryKeyword();
            categoryKeyword.setCategory(categoryList.get(0));
            categoryKeyword.setKeyword(keyword);
            categoryKeywordList.add(categoryKeyword);
        }

        categoryKeywordRepository.saveAll(categoryKeywordList);
    }
}
