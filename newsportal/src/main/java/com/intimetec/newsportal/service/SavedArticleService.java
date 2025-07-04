package com.intimetec.newsportal.service;


import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.SaveOrRemoveArticleDTO;

import java.util.List;

public interface SavedArticleService {
    public void saveUserArticle(SaveOrRemoveArticleDTO saveUserArticleRequestDTO);
    public void deleteSavedArticle(SaveOrRemoveArticleDTO saveUserArticleRequestDTO);
    public List<ArticleDTO> getSavedArticles(Long userId);
}
