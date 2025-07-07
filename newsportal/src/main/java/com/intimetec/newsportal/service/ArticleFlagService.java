package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ArticleReportRequestDTO;
import com.intimetec.newsportal.dto.ReportedArticleSummaryDTO;

import java.util.List;

public interface ArticleFlagService {
    public void flagArticle(ArticleReportRequestDTO articleReportRequestDTO);
    public List<ReportedArticleSummaryDTO> getReportedArticleSummary();
}
