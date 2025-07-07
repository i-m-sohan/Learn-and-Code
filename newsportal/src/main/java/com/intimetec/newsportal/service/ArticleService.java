package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.dto.HeadlineRequestDTO;
import com.intimetec.newsportal.dto.ReportedArticleSummaryDTO;
import com.intimetec.newsportal.model.Article;

import java.util.List;

public interface ArticleService {
    public List<Article> saveArticles(List<ArticleDTO> articleDTOList);
    public List<ArticleDTO> getHeadlineArticles(HeadlineRequestDTO headlineRequestDTO);
    public List<ArticleDTO> searchArticles(String keyword);
    public void hidArticle(Integer articleId);
    public void hideArticleByCategory(Integer categoryId);
    public List<ArticleDTO> fetchArticleFromExternalSources();
}
