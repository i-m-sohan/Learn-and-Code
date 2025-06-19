package com.intimetec.newsportal.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.service.ExternalNewsSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsProviderFactory {

    private ExternalNewsSourceService externalNewsSourceService;
    private ObjectMapper objectMapper;
    private NewsClient newsClient;

    @Autowired
    public NewsProviderFactory(ExternalNewsSourceService externalNewsSourceService,ObjectMapper objectMapper,NewsClient newsClient){
        this.externalNewsSourceService = externalNewsSourceService;
        this.objectMapper = objectMapper;
        this.newsClient = newsClient;
    }

    public NewsClient getBestNewsApiClient(){
        List<ExternalNewsSourceDTO> externalNewsSourceDTOList =  externalNewsSourceService.getAllExternalNewsSources();
        System.out.println("Printing dto while getting best client");
        System.out.println(externalNewsSourceDTOList.get(0).toString());

        newsClient.setBaseUrl(externalNewsSourceDTOList.get(0).getBaseUrl());
        newsClient.setApiKey(externalNewsSourceDTOList.get(0).getApiKey());
        return newsClient;
    }
}
