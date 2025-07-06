package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Integer> {

    List<ArticleCategory> findByArticleId(Integer articleId);

    List<ArticleCategory> findByCategoryId(Integer categoryId);

    void deleteByArticleIdAndCategoryId(Integer articleId, Integer categoryId);

    boolean existsByArticleIdAndCategoryId(Integer articleId, Integer categoryId);

    List<ArticleCategory> findByArticleIdIn(List<Integer> articleIds);
}
