package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArticleMapper {

    public static ArticleDTO toDTO(Article article) {
        if (article == null) return null;

        ArticleDTO dto = new ArticleDTO();
        dto.setArticleId(article.getArticleId());
        dto.setTitle(article.getTitle());
        dto.setDescription(article.getDescription());
        dto.setContent(article.getContent());
        dto.setSource(article.getSource());
        dto.setUrl(article.getUrl());
        dto.setPublishedDate(article.getPublishedDate());


        List<String> categoryNamesList = new ArrayList<>();
        for(Category category : article.getCategories()){
            categoryNamesList.add(category.getCategoryName());
        }
        dto.setCategories(categoryNamesList);
        return dto;
    }

    public static Article toEntity(ArticleDTO dto) {
        if (dto == null) return null;

        Article article = new Article();
        article.setArticleId(dto.getArticleId());
        article.setTitle(dto.getTitle());
        article.setDescription(dto.getDescription());
        article.setContent(dto.getContent());
        article.setSource(dto.getSource());
        article.setUrl(dto.getUrl());
        article.setPublishedDate(dto.getPublishedDate());

        return article;
    }

    public static List<Article> toEntityList(List<ArticleDTO> articleDTOs) {
        if (articleDTOs == null) return List.of();

        List<Article> articleList = new ArrayList<>();
        for(ArticleDTO articleDTO : articleDTOs){
            articleList.add(toEntity(articleDTO));
        }

        return articleList;
    }
}
