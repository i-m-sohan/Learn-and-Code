package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface KeywordService {
    public void saveUserCategoryKeyword(Long userId, CategoryKeywordDTO categoryKeywordDTO);
//    public void addUCategoryKeyword(@RequestBody CategoryKeywordDTO categoryKeywordDTO);
    public void removeUserCategoryKeyword(Long userId, CategoryKeywordDTO categoryKeywordDTO);
}
