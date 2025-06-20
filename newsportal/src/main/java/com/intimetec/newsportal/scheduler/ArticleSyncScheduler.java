package com.intimetec.newsportal.scheduler;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.factory.NewsProviderFactory;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.repository.CategoryRepository;
import com.intimetec.newsportal.service.ArticleService;
import com.intimetec.newsportal.service.CategoryService;
import com.intimetec.newsportal.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ArticleSyncScheduler {

    @Autowired
    private NewsProviderFactory newsProviderFactory;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 */3 * * *")
    public void fetchArticle() {
        List<ArticleDTO> articleDTOList = newsProviderFactory.getBestNewsApiClient().getArticlesPeriodically();

        List<Article> articleList =  articleService.saveArticles(articleDTOList);

        System.out.println("ARticles List : ");
        for(Article article : articleList){
            System.out.println(article.toString());
        }
        notificationService.notifyUsersForMatchingArticles(articleList);
    }
}
