package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword,String> {
    List<Keyword> findByKeywordIn(List<String> keywords);
}
