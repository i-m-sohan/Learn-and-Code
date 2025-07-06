package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.ArticleFlagRequestDTO;
import com.intimetec.newsportal.dto.ReportedArticleDTO;
import com.intimetec.newsportal.dto.ReportedArticleSummaryDTO;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.ReportedArticle;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.mapper.ReportedArticleMapper;
import com.intimetec.newsportal.repository.ReportedArticleRepository;
import com.intimetec.newsportal.repository.UserRepository;
import com.intimetec.newsportal.service.ArticleFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleFlagServiceImpl implements ArticleFlagService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReportedArticleRepository reportedArticleRepository;

    @Override
    public void flagArticle(ArticleFlagRequestDTO articleFlagRequestDTO){
        ReportedArticle report = ReportedArticleMapper.toEntity(articleFlagRequestDTO);
        reportedArticleRepository.save(report);
    }

    @Override
    public List<ReportedArticleSummaryDTO> getReportedArticleSummary() {
        System.out.println("In service");
        List<Article> flaggedArticles = articleRepository.findByReportCountGreaterThan(2);
        System.out.println("flaggedArticles :");
        System.out.println(flaggedArticles);
        List<ArticleDTO> articleDTOList = ArticleMapper.toDTOList(flaggedArticles);
        List<Integer> articleIds = new ArrayList<>();
        for (ArticleDTO articleDTO : articleDTOList) {
            articleIds.add(articleDTO.getArticleId());
        }
        System.out.println(articleIds);

        List<ReportedArticle> allReports = reportedArticleRepository.findByArticleIdIn(articleIds);
        List<ReportedArticleDTO> reportedArticleDTOS = ReportedArticleMapper.toDTOList(allReports);

        Map<Integer, List<String>> articleIdToReasonsMap = new HashMap<>();
        for (ReportedArticleDTO reportedArticleDTO : reportedArticleDTOS) {
            Integer articleId = reportedArticleDTO.getArticleId();
            if (!articleIdToReasonsMap.containsKey(articleId)) {
                articleIdToReasonsMap.put(articleId, new ArrayList<>());
            }
            List<String> reasons = articleIdToReasonsMap.get(articleId);
            if (reportedArticleDTO.getReason() != null && !reasons.contains(reportedArticleDTO.getReason())) {
                reasons.add(reportedArticleDTO.getReason());
            }
        }

        List<ReportedArticleSummaryDTO> reportedArticleSummaryDTOS = new ArrayList<>();
        for (Article article : flaggedArticles) {
            List<String> reasons = articleIdToReasonsMap.getOrDefault(article.getArticleId(), new ArrayList<>());

            ReportedArticleSummaryDTO dto = new ReportedArticleSummaryDTO(
                    article.getArticleId(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getContent(),
                    article.getReportCount(),
                    reasons
            );

            reportedArticleSummaryDTOS.add(dto);
        }

        return reportedArticleSummaryDTOS;
    }

}
