package com.intimetec.newsportal.scheduler;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.factory.NewsProviderFactory;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.repository.CategoryRepository;
import com.intimetec.newsportal.service.ArticleService;
import com.intimetec.newsportal.service.CategoryService;
import com.intimetec.newsportal.service.ExternalNewsSourceService;
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

    @Autowired
    private ExternalNewsSourceService externalNewsSourceService;

    @Scheduled(cron = "0 0 */3 * * *")
    public void fetchArticle() {
        List<ArticleDTO> articleDTOList = articleService.fetchArticleFromExternalSources();
        categoryService.defineArticlesCategory(articleDTOList);
        articleService.saveArticles(articleDTOList);
//        for(ArticleDTO articleDTO : articleDTOList){
//            System.out.println("-----------------------------------------------------------------------");
//            System.out.println(articleDTO.toString());
//            System.out.println("-----------------------------------------------------------------------");
//        }

//        List<Article> articleList =  articleService.saveArticles(articleDTOList);
//        notificationService.notifyUsersForMatchingArticles(articleList);
    }
}
