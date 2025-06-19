package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
}
