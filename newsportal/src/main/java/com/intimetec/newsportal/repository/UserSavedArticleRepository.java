package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Article;
import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.UserSavedArticle;
import com.intimetec.newsportal.model.UserSavedArticle.UserSavedArticleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSavedArticleRepository extends JpaRepository<UserSavedArticle, UserSavedArticleId> {

    boolean existsByUserAndArticle(User user, Article article);

    List<UserSavedArticle> findByUser(User user);

    void deleteByUserAndArticle(User user, Article article);
}
