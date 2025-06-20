package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.HeadlineRequestDTO;
import com.intimetec.newsportal.dto.SaveUserArticleRequestDTO;
import com.intimetec.newsportal.model.Article;

import java.util.List;

public interface ArticleService {
    public abstract List<Article> saveArticles(List<ArticleDTO> articleDTOList);
    public List<ArticleDTO> getHeadlineArticles(HeadlineRequestDTO headlineRequestDTO);
    public void saveUserArticles(SaveUserArticleRequestDTO saveUserArticleRequestDTO);
}
