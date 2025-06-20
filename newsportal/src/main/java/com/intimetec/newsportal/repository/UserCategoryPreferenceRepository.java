package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.UserCategoryPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryPreferenceRepository extends JpaRepository<UserCategoryPreference,Long> {
//    public UserCategoryPreference findByUserAndCategory(Long userId,Integer categoryId);
}
