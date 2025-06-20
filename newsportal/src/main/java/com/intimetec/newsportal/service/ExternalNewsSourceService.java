package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.dto.ExternalServerDetailDTO;
import com.intimetec.newsportal.dto.ExternalServerStatusDTO;
import com.intimetec.newsportal.dto.ExternalServerUpdateDTO;
import com.intimetec.newsportal.model.ExternalNewsSource;

import java.util.List;

public interface ExternalNewsSourceService {
    public List<ExternalNewsSourceDTO> getAllExternalNewsSources();
    public List<ExternalServerStatusDTO> getAllExternalServerStatuses();
    public List<ExternalServerDetailDTO> getAllExternalServerDetails();
    public void updateServerDetails(ExternalServerUpdateDTO externalServerUpdateDTO);
    public List<ExternalNewsSourceDTO> findAllByOrderByLastAccessedAsc();
    public void updateNewsSourceLastAccessed(ExternalNewsSourceDTO externalNewsSourceDTO);
}
