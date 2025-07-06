package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public List<Category> findByCategoryNameIn(Collection<String> categoryNames);
    public Category findByCategoryName(String categoryName);
    List<Category> findByCategoryNameInAndIsVisibleTrue(Collection<String> categoryNames);
    Category findByCategoryNameAndIsVisibleTrue(String categoryName);
}
