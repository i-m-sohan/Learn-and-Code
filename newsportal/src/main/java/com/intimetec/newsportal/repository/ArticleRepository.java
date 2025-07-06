package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Integer> {
    List<Article> findByPublishedDateBetweenAndIsVisibleTrue(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT a FROM Article a WHERE " +
            "LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Article> searchByKeyword(@Param("keyword") String keyword);

    List<Article> findByReportCountGreaterThan(int reportCount);

    @Query("SELECT article FROM Article article JOIN article.categories category " +
            "WHERE category.categoryId = :categoryId AND article.isVisible = true")
    List<Article> findVisibleArticlesByCategoryId(@Param("categoryId") Integer categoryId);

}
