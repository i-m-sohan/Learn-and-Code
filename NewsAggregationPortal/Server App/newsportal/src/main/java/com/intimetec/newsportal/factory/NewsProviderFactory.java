package com.intimetec.newsportal.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.service.ExternalNewsSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NewsProviderFactory {

    private final Map<String, NewsClient> clientMap;

    @Autowired
    private ExternalNewsSourceService externalNewsSourceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public NewsProviderFactory(
            @Qualifier("newsApiClient") NewsClient newsApiClient,
            @Qualifier("theNewsApiClient") NewsClient theNewsApiClient
    ) {
        clientMap = new HashMap<>();
        clientMap.put("news api", newsApiClient);           // key as sourceName
        clientMap.put("The News API", theNewsApiClient);
    }

    public NewsClient getClientBySource(String sourceName) {
        NewsClient client = clientMap.get(sourceName.toLowerCase());
        if (client == null) throw new IllegalArgumentException("Unsupported source: " + sourceName);
        return client;
    }



    public NewsClient getBestNewsApiClient(){
        List<ExternalNewsSourceDTO> externalNewsSourceDTOList =  externalNewsSourceService.getAllExternalNewsSources();
        System.out.println("Printing dto while getting best client");
        System.out.println(externalNewsSourceDTOList.get(1).toString());
        System.out.println("Beseurl =");
        System.out.println(externalNewsSourceDTOList.get(1).getBaseUrl());
        NewsClient newsClient = getClientBySource(externalNewsSourceDTOList.get(1).getSourceName());
        newsClient.setBaseUrl(externalNewsSourceDTOList.get(1).getBaseUrl());
        newsClient.setApiKey(externalNewsSourceDTOList.get(1).getApiKey());
        return newsClient;
    }
}

