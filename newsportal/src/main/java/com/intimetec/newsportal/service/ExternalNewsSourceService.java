package com.intimetec.newsportal.service;

import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;

import java.util.List;

public interface ExternalNewsSourceService {
    public List<ExternalNewsSourceDTO> getAllExternalNewsSources();
}
