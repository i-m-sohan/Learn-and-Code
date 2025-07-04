package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.ReportedArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportedArticleRepository extends JpaRepository<ReportedArticle, Long> {

    int countByArticle(Article article);

}

