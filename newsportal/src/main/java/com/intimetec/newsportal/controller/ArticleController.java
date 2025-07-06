package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.*;
import com.intimetec.newsportal.service.ArticleFlagService;
import com.intimetec.newsportal.service.ArticleReactionService;
import com.intimetec.newsportal.service.ArticleService;
import com.intimetec.newsportal.service.SavedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ArticleReactionService articleReactionService;

    @Autowired
    ArticleFlagService articleFlagService;

    @PostMapping("/headlines")
    public ResponseEntity<?> getHeadlines(@RequestBody HeadlineRequestDTO headlineRequestDTO){
        List<ArticleDTO> articleDTOList= articleService.getHeadlineArticles(headlineRequestDTO);
        return new ResponseEntity<>(articleDTOList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUserArticles(@RequestBody SaveOrRemoveArticleDTO saveUserArticleRequestDTO){
        savedArticleService.saveUserArticle(saveUserArticleRequestDTO);
        return new ResponseEntity<>(Map.of("Message","Article Save Succesfully"),HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUserSavedArticles(@RequestBody SaveOrRemoveArticleDTO deleteUserSavedArticleDTO){
        savedArticleService.deleteSavedArticle(deleteUserSavedArticleDTO);
        return new ResponseEntity<>(Map.of("Message","Article deleted Succesfully"),HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchArticle(@RequestBody ArticleSearchRequestDTO articleSearchRequestDTO){
        List<ArticleDTO> articleDTOList =  articleService.searchArticles(articleSearchRequestDTO.getKeyword());
        return new ResponseEntity<>(Map.of("Articles",articleDTOList),HttpStatus.OK);
    }

    @PostMapping("/react")
    public ResponseEntity<?> likeArticle(@RequestBody ArticleReactionRequestDTO articleReactionRequestDTO){
        articleReactionService.handleArticelReaction(articleReactionRequestDTO);
        return new ResponseEntity<>(Map.of("Message","Reaction" + articleReactionRequestDTO.getReactionType() + " Successfully!!"),HttpStatus.OK);
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportArticle(@RequestBody ArticleFlagRequestDTO articleFlagRequestDTO){
        articleFlagService.flagArticle(articleFlagRequestDTO);
        return new ResponseEntity<>(Map.of("Message","Article Reported Successfully!"),HttpStatus.OK);
    }

    @GetMapping("/saved-articles/{userId}")
    public ResponseEntity<?> getSavedArticles(@PathVariable Long userId){
        List<ArticleDTO> articleDTOList =  savedArticleService.getSavedArticles(userId);
        return new ResponseEntity<>(Map.of("Saved Articles",articleDTOList),HttpStatus.OK);
    }

    @GetMapping("/reported-summary")
    public ResponseEntity<?> getReportedArticlesSummary(){
        System.out.println("In controller");
        List<ReportedArticleSummaryDTO> reportedArticleSummaryDTOList = articleFlagService.getReportedArticleSummary();
        return new ResponseEntity<>(Map.of("Reported Articles's Summary",reportedArticleSummaryDTOList),HttpStatus.OK);
    }

    @PostMapping("/admin/hide/{articleId}")
    public ResponseEntity<?> hideArticle(@PathVariable Integer articleId) {
        articleService.hidArticle(articleId);
        return new ResponseEntity<>(Map.of("Message","Article Hidden Successfully!"),HttpStatus.OK);
    }

}
