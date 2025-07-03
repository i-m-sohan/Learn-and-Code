package com.intimetec.newsportal;

import com.intimetec.newsportal.dto.ArticleDTO;
import com.intimetec.newsportal.scheduler.ArticleSyncScheduler;
import com.intimetec.newsportal.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class NewsAggregationPortalApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(NewsAggregationPortalApplication.class, args);
	}

}
