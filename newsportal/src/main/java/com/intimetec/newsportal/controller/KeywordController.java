package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.service.KeywordService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/newsportal.intimetec.com/v1")
public class KeywordController {

    @Autowired
    KeywordService keywordService;

    @PostMapping("/user-category-keyword/{userId}")
    public ResponseEntity<?> addUserCategoryKeywordPreference(@PathVariable Long userId, @RequestBody CategoryKeywordDTO categoryKeywordDTO){
        keywordService.saveUserCategoryKeyword(userId,categoryKeywordDTO);
        return new ResponseEntity<>(Map.of("Messsage","Keyword successfuly added for the category"), HttpStatus.CREATED);
    }

    @DeleteMapping("/user-category-keyword/{userId}")
    public ResponseEntity<?> removeUserCategoryKeywordPreference(@PathVariable Long userId, @RequestBody CategoryKeywordDTO categoryKeywordDTO){
        keywordService.removeUserCategoryKeyword(userId,categoryKeywordDTO);
        return new ResponseEntity<>(Map.of("Messsage","Keywords successfuly removed for the category"), HttpStatus.CREATED);
    }

}
