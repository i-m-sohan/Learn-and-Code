package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.model.CategoryKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryKeywordRepository extends JpaRepository<CategoryKeyword,Long> {
    List<CategoryKeyword> findByCategoryIn(List<Category> categories);
}
