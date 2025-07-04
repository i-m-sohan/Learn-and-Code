package com.intimetec.newsportal.service.serviceImpl;

import com.intimetec.newsportal.dto.ArticleFlagRequestDTO;
import com.intimetec.newsportal.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleFlagServiceImpl {

    @Autowired
    ArticleRepository articleRepository;

    public void flagArticle(ArticleFlagRequestDTO articleFlagRequestDTO){

    }
}
