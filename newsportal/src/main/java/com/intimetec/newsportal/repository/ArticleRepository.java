package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findByPublishedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT a FROM Article a WHERE " +
            "LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Article> searchByKeyword(@Param("keyword") String keyword);
}
