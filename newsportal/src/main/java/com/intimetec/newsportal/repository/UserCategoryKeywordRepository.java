package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.UserCategoryKeyword;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.Category;
import com.intimetec.newsportal.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCategoryKeywordRepository extends JpaRepository<UserCategoryKeyword, Long> {

    List<UserCategoryKeyword> findByUserAndCategory(User user, Category category);

    boolean existsByUserAndCategoryAndKeyword(User user, Category category, Keyword keyword);

    void deleteByUserAndCategoryAndKeyword(User user, Category category, Keyword keyword);

    List<UserCategoryKeyword> findByUser(User user);

    List<UserCategoryKeyword> findByUserAndCategoryAndKeywordIn(User user, Category category, List<Keyword> keywords);

}
