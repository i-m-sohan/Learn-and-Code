package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.ArticleReportRequestDTO;
import com.intimetec.newsportal.dto.ReportedArticleDTO;
import com.intimetec.newsportal.dto.ReportedArticleSummaryDTO;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.ArticleCategory;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.model.ReportedArticle;
import com.intimetec.newsportal.repository.*;
import com.intimetec.newsportal.mapper.ReportedArticleMapper;
import com.intimetec.newsportal.service.ArticleFlagService;
import com.intimetec.newsportal.service.EmailService;
import com.intimetec.newsportal.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleFlagServiceImpl implements ArticleFlagService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    ReportedArticleRepository reportedArticleRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    NotificationService notificationService;

    @Override
    public void flagArticle(ArticleReportRequestDTO articleReportRequestDTO){
        ReportedArticle reportedArticle = ReportedArticleMapper.toEntity(articleReportRequestDTO);

        reportedArticleRepository.save(reportedArticle);

        Article article = articleRepository.getReferenceById(articleReportRequestDTO.getArticleId());
        List<ArticleCategory> articleCategoryList = articleCategoryRepository.findByArticleId(article.getArticleId());

        Set<Integer> categoryIds = new HashSet<>();
        for(ArticleCategory articleCategory : articleCategoryList){
            categoryIds.add(articleCategory.getCategoryId());
        }

        List<Category> categoryList = categoryRepository.findAllById(categoryIds);
        System.out.println("Reporting the article : " + article.getArticleId());

        for(Category category : categoryList){
            System.out.println(category.getCategoryName());
        }

        ArticleDTO articleDTO = ArticleMapper.toDTO(article);
        if(articleDTO.getReportCount()>3){
            articleDTO.setIsVisible(false);
        }
        Article articleUpdated = ArticleMapper.toEntity(articleDTO);
        articleUpdated.setCategories(new HashSet<>(categoryList));
        articleRepository.save(articleUpdated);
    }

    @Override
    public List<ReportedArticleSummaryDTO> getReportedArticleSummary() {
        List<Article> flaggedArticles = articleRepository.findVisibleReportedArticles(0);
        List<ArticleDTO> articleDTOList = ArticleMapper.toDTOList(flaggedArticles);

        List<Integer> articleIds = new ArrayList<>();
        for (ArticleDTO articleDTO : articleDTOList) {
            articleIds.add(articleDTO.getArticleId());
        }

        List<ReportedArticle> allReports = reportedArticleRepository.findByArticleIdIn(articleIds);
        List<ReportedArticleDTO> reportedArticleDTOS = ReportedArticleMapper.toDTOList(allReports);

        Map<Integer, List<String>> articleIdToReasonsMap = new HashMap<>();
        for (ReportedArticleDTO reportedArticleDTO : reportedArticleDTOS) {
            Integer articleId = reportedArticleDTO.getArticleId();
            articleIdToReasonsMap
                    .computeIfAbsent(articleId, k -> new ArrayList<>())
                    .add(reportedArticleDTO.getReason());
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
