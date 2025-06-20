package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.HeadlineRequestDTO;
import com.intimetec.newsportal.dto.SaveUserArticleRequestDTO;
import com.intimetec.newsportal.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newsportal.intimetec.com/v1")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/headlines")
    public ResponseEntity<?> getHeadlines(@RequestBody HeadlineRequestDTO headlineRequestDTO){
        List<ArticleDTO> articleDTOList= articleService.getHeadlineArticles(headlineRequestDTO);
        return new ResponseEntity<>(articleDTOList, HttpStatus.OK);
    }

    public ResponseEntity<?> saveUserArticles(@RequestBody SaveUserArticleRequestDTO saveUserArticleRequestDTO){

    }
}
