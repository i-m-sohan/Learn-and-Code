package com.intimetec.newsportal;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.mapper.ArticleMapper;
import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.repository.ArticleRepository;
import com.intimetec.newsportal.scheduler.ArticleSyncScheduler;
import com.intimetec.newsportal.service.EmailService;
import com.intimetec.newsportal.service.PersonalizationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class NewsAggregationPortalApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(NewsAggregationPortalApplication.class, args);
		ArticleRepository articleRepository = context.getBean(ArticleRepository.class);
		List<Article> articleList = articleRepository.findAll();
		ArticleSyncScheduler articleSyncScheduler = context.getBean(ArticleSyncScheduler.class);
		articleSyncScheduler.fetchArticle();
	}

}
