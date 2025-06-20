package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.ExternalNewsSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalNewsSourceRepository extends JpaRepository<ExternalNewsSource,Long> {
}
