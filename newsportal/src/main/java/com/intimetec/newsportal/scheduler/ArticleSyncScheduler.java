package com.intimetec.newsportal.scheduler;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.factory.NewsProviderFactory;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleSyncScheduler {

    @Autowired
    private NewsProviderFactory newsProviderFactory;

    @Autowired
    private ArticleRepository articleRepository;

    @Scheduled(cron = "0 0 */3 * * *")
    public void fetchArticle() {
        List<ArticleDTO> articleDTOList = newsProviderFactory.getBestNewsApiClient().getArticlesPeriodically();
        List<Article> articleList = ArticleMapper.toEntityList(articleDTOList);

        articleRepository.saveAll(articleList);

        for(Article article : articleList){
            System.out.println(article.toString());
        }
    }
}
