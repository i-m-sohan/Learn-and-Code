package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ArticleDTO;

import java.util.List;

public interface PersonalizationService {
    List<ArticleDTO> getPersonalizedArticles(Long userId, List<ArticleDTO> articleDTOList);
}
