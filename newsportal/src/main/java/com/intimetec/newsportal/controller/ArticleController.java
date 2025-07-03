package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.ArticleSearchRequestDTO;
import com.intimetec.newsportal.dto.HeadlineRequestDTO;
import com.intimetec.newsportal.dto.UserArticleRequestDTO;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.UserSavedArticle;
import com.intimetec.newsportal.service.ArticleService;
import com.intimetec.newsportal.service.SavedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/newsportal.intimetec.com/v1/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    SavedArticleService savedArticleService;

    @PostMapping("/headlines")
    public ResponseEntity<?> getHeadlines(@RequestBody HeadlineRequestDTO headlineRequestDTO){
        List<ArticleDTO> articleDTOList= articleService.getHeadlineArticles(headlineRequestDTO);
        return new ResponseEntity<>(articleDTOList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUserArticles(@RequestBody UserArticleRequestDTO saveUserArticleRequestDTO){
        savedArticleService.saveUserArticle(saveUserArticleRequestDTO);
        return new ResponseEntity<>(Map.of("Message","Article Save Succesfully"),HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUserSavedArticles(@RequestBody UserArticleRequestDTO deleteUserSavedArticleDTO){
        savedArticleService.deleteSavedArticle(deleteUserSavedArticleDTO);
        return new ResponseEntity<>(Map.of("Message","Article deleted Succesfully"),HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchArticle(@RequestBody ArticleSearchRequestDTO articleSearchRequestDTO){
        List<ArticleDTO> articleDTOList =  articleService.searchArticles(articleSearchRequestDTO.getKeyword());
        return new ResponseEntity<>(Map.of("Articles",articleDTOList),HttpStatus.OK);
    }

    @PatchMapping("/like")
    public ResponseEntity<?> likeArticle(@RequestBody ArticleSearchRequestDTO articleSearchRequestDTO){
        List<ArticleDTO> articleDTOList =  articleService.searchArticles(articleSearchRequestDTO.getKeyword());
        return new ResponseEntity<>(Map.of("Articles",articleDTOList),HttpStatus.OK);
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportArticle(@RequestBody ArticleSearchRequestDTO articleSearchRequestDTO){
        List<ArticleDTO> articleDTOList =  articleService.searchArticles(articleSearchRequestDTO.getKeyword());
        return new ResponseEntity<>(Map.of("Articles",articleDTOList),HttpStatus.OK);
    }

    @GetMapping("/saved-articles/{userId}")
    public ResponseEntity<?> getSavedArticles(@PathVariable Long userId){
        List<ArticleDTO> articleDTOList =  savedArticleService.getSavedArticles(userId);
        return new ResponseEntity<>(Map.of("Saved Articles",articleDTOList),HttpStatus.OK);
    }
}
