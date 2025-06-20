package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
