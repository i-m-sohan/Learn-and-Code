package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ArticleFlagRequestDTO;
import com.intimetec.newsportal.dto.ReportedArticleSummaryDTO;

import java.util.List;

public interface ArticleFlagService {
    public void flagArticle(ArticleFlagRequestDTO articleFlagRequestDTO);
    public List<ReportedArticleSummaryDTO> getReportedArticleSummary();
}
