package com.intimetec.newsportal.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intimetec.newsportal.client.NewsClient;
import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.service.ExternalNewsSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            @Qualifier("news api") NewsClient newsApiClient,
            @Qualifier("The News API") NewsClient theNewsApiClient
    ) {
        clientMap = new HashMap<>();
        clientMap.put("news api", newsApiClient);           // key as sourceName
        clientMap.put("The News API", theNewsApiClient);
    }

    public NewsClient getClientBySource(String sourceName) {
        NewsClient client = clientMap.get(sourceName);
        if (client == null) throw new IllegalArgumentException("Unsupported source: " + sourceName);
        return client;
    }

   public List<NewsClient> getAvailableNewsClients(){
        List<ExternalNewsSourceDTO> externalNewsSourceDTOList = externalNewsSourceService.getAllAvailableExternalNewsSources();
        List<NewsClient> newsClientList = new ArrayList<>();
        for(ExternalNewsSourceDTO externalNewsSourceDTO : externalNewsSourceDTOList){
            externalNewsSourceDTO.setLastAccessed(LocalDateTime.now());
            externalNewsSourceService.updateNewsSourceLastAccessed(externalNewsSourceDTO);
            NewsClient newsClient = getClientBySource(externalNewsSourceDTO.getSourceName());
            newsClient.setApiKey(externalNewsSourceDTO.getApiKey());
            newsClient.setBaseUrl(externalNewsSourceDTO.getBaseUrl());
            newsClientList.add(newsClient);
        }
        return newsClientList;
    }
}

