package com.intimetec.newsportal.scheduler;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.factory.NewsProviderFactory;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleSyncScheduler {

    @Autowired
    private NewsProviderFactory newsProviderFactory;

    @Scheduled(cron = "0 0 */3 * * *")  // Every 3 hours
    public List<ArticleDTO> fetchLatestArticles() {
        List<ArticleDTO> articleDTOList = newsProviderFactory.getBestNewsApiClient().getArticlesPeriodically();
        List<Article> articleList = ArticleMapper.toEntityList(articleDTOList);
        return articleDTOList;
    }
}
