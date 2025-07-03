package com.intimetec.newsportal.service;


import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.UserArticleRequestDTO;

import java.util.List;

public interface SavedArticleService {
    public void saveUserArticle(UserArticleRequestDTO saveUserArticleRequestDTO);
    public void deleteSavedArticle(UserArticleRequestDTO saveUserArticleRequestDTO);
    public List<ArticleDTO> getSavedArticles(Long userId);
}
