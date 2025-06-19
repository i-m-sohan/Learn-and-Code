package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.model.ExternalNewsSource;
import org.springframework.stereotype.Component;

@Component
public class ExternalNewsSourceMapper {

    public static ExternalNewsSourceDTO toDTO(ExternalNewsSource entity) {
        if (entity == null) return null;

        ExternalNewsSourceDTO dto = new ExternalNewsSourceDTO();
        dto.setSourceId(entity.getSourceId());
        dto.setSourceName(entity.getSourceName());
        dto.setApiKey(entity.getApiKey());
        dto.setBaseUrl(entity.getBaseUrl());
        dto.setStatus(entity.getStatus());
        dto.setLastAccessed(entity.getLastAccessed());

        return dto;
    }

    public static ExternalNewsSource toEntity(ExternalNewsSourceDTO dto) {
        if (dto == null) return null;

        ExternalNewsSource entity = new ExternalNewsSource();
        entity.setSourceId(dto.getSourceId());
        entity.setSourceName(dto.getSourceName());
        entity.setApiKey(dto.getApiKey());
        entity.setBaseUrl(dto.getBaseUrl());
        entity.setStatus(dto.getStatus());
        entity.setLastAccessed(dto.getLastAccessed());

        return entity;
    }
}

