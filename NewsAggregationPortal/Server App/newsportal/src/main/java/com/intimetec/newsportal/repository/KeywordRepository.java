package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword,String> {
}
