package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.User;
import com.intimetec.newsportal.model.UserCategoryPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryPreferenceRepository extends JpaRepository<UserCategoryPreference,Long> {
//    public UserCategoryPreference findByUserAndCategory(Long userId,Integer categoryId);
    List<UserCategoryPreference> findByUserId(Long userId);
    UserCategoryPreference findByUserIdAndCategoryId(Long userId,Integer categoryId);
}
