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
		SpringApplication.run(NewsAggregationPortalApplication.class, args);
//		ArticleSyncScheduler articleSyncScheduler = context.getBean(ArticleSyncScheduler.class);
//		List<ArticleDTO> articleDTOList = articleSyncScheduler.fetchLatestArticles();
//		String emailBody = "";
//		for(ArticleDTO articleDTO : articleDTOList){
//			emailBody += articleDTO.getTitle() + "\n" + "URL : " + articleDTO.getUrl() + "\n";
//		}
//		EmailService emailService = context.getBean(EmailService.class);
//		emailService.sendEmail(emailBody);
	}

}
