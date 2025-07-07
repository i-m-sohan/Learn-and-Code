package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.exception.EntityNotFoundException;
import com.intimetec.newsportal.model.*;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.service.CategoryService;
import com.intimetec.newsportal.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCategoryKeywordRepository userCategoryKeywordRepository;

    @Override
    public void saveUserCategoryKeyword(Long userId, CategoryKeywordDTO categoryKeywordDTO) {
        System.out.println("Inside service method");
        User user = userRepository.getReferenceById(userId);
        Category category = categoryRepository.findByCategoryName(categoryKeywordDTO.getCategoryName());

        if(category==null){
            System.out.println("Category is null");
            throw new EntityNotFoundException("Category : " + categoryKeywordDTO.getCategoryName() + " Not found!");
        }

        List<String> keywordNames = categoryKeywordDTO.getKeywords();
        List<Keyword> existingKeywords = keywordRepository.findByKeywordIn(keywordNames);
        Set<String> existingKeywordNames = getExistingKeywordNames(existingKeywords);

        for(Keyword keyword : existingKeywords){
            System.out.println(keyword.toString());
        }

        List<Keyword> newKeywords = getNewKeywords(keywordNames,existingKeywordNames);

        List<Keyword> savedNewKeywords = keywordRepository.saveAll(newKeywords);
        List<Keyword> keywordEntityList = new ArrayList<>();
        List<Keyword> allKeywords = new ArrayList<>(existingKeywords);
        allKeywords.addAll(savedNewKeywords);

        System.out.println("-------------------------");
        for(Keyword keyword : allKeywords){
            System.out.println(keyword.toString());
        }
        for (Keyword keyword : allKeywords) {
            boolean exists = userCategoryKeywordRepository
                    .existsByUserAndCategoryAndKeyword(user, category, keyword);
            if (!exists) {
                UserCategoryKeyword userCategoryKeyword = new UserCategoryKeyword(user, category, keyword);
                userCategoryKeywordRepository.save(userCategoryKeyword);
            }
        }
    }

    private Set<String> getExistingKeywordNames(List<Keyword> existingKeywords){
        Set<String> existingKeywordNames = existingKeywords.stream()
                .map(Keyword::getKeyword)
                .collect(Collectors.toSet());
        return existingKeywordNames;
    }

    private List<Keyword> getNewKeywords(List<String> keywordNames, Set<String> existingKeywordNames){
        List<Keyword> newKeywords = keywordNames.stream()
                .filter(keywordName -> !existingKeywordNames.contains(keywordName))
                .map(kw -> {
                    Keyword keyword = new Keyword();
                    keyword.setKeyword(kw);
                    return keyword;
                })
                .collect(Collectors.toList());
        return newKeywords;
    }

    public void removeUserCategoryKeyword(Long userId, CategoryKeywordDTO categoryKeywordDTO){
        User user = userRepository.getReferenceById(userId);
        Category category = categoryRepository.findByCategoryName(categoryKeywordDTO.getCategoryName());

        if(category == null){
            throw new EntityNotFoundException("Category : " + categoryKeywordDTO.getCategoryName() + " Not found!");
        }

        List<String> keywordNames = categoryKeywordDTO.getKeywords();
        List<Keyword> keywords = keywordRepository.findByKeywordIn(keywordNames);

        List<UserCategoryKeyword> userCategoryKeywordToDelete = userCategoryKeywordRepository
                .findByUserAndCategoryAndKeywordIn(user, category, keywords);

        if (!userCategoryKeywordToDelete.isEmpty()) {
            userCategoryKeywordRepository.deleteAll(userCategoryKeywordToDelete);
        }
    }
}
