package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsportal.intimetec.com/v1")
public class KeywordController {

    @Autowired
    KeywordService keywordService;

    @PostMapping("/category-keyword")
    public void addUCategoryKeyword(@RequestBody CategoryKeywordDTO categoryKeywordDTO){
        keywordService.addUCategoryKeyword(categoryKeywordDTO);
    }
}
