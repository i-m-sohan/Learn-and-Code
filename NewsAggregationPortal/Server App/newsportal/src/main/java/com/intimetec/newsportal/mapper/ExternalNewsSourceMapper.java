package com.intimetec.newsportal.mapper;

import com.intimetec.newsportal.dto.ExternalNewsSourceDTO;
import com.intimetec.newsportal.dto.ExternalServerDetailDTO;
import com.intimetec.newsportal.dto.ExternalServerStatusDTO;
import com.intimetec.newsportal.model.ExternalNewsSource;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExternalNewsSourceMapper {

    public static ExternalNewsSourceDTO toExternalNewsSourceDTO(ExternalNewsSource entity) {
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
    public static ExternalServerStatusDTO toStatusDTO(ExternalNewsSource entity) {
        if (entity == null) return null;

        String formattedLastAccessed = entity.getLastAccessed() != null
                ? entity.getLastAccessed().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                : "Never";

        return new ExternalServerStatusDTO(
                entity.getSourceName(),
                Boolean.TRUE.equals(entity.getStatus()),
                formattedLastAccessed
        );
    }

    public static ExternalServerDetailDTO toDetailDTO(ExternalNewsSource entity) {
        if (entity == null) return null;

        String formattedLastAccessed = entity.getLastAccessed() != null
                ? entity.getLastAccessed().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                : "Never";

        return new ExternalServerDetailDTO(
                entity.getSourceName(),
                entity.getApiKey()
        );
    }

    public static List<ExternalServerStatusDTO> toStatusDTOList(List<ExternalNewsSource> sources) {
        if (sources == null || sources.isEmpty()) return List.of();

        List<ExternalServerStatusDTO> externalServerStatusDTOList = new ArrayList<>();

        for(ExternalNewsSource externalNewsSource : sources){
            externalServerStatusDTOList.add(toStatusDTO(externalNewsSource));
        }
        return externalServerStatusDTOList;
    }

    public static List<ExternalServerDetailDTO> toDetailDTOList(List<ExternalNewsSource> sources) {
        if (sources == null || sources.isEmpty()) return List.of();

        List<ExternalServerDetailDTO> externalServerDetailDTOList = new ArrayList<>();

        for(ExternalNewsSource externalNewsSource : sources){
            externalServerDetailDTOList.add(toDetailDTO(externalNewsSource));
        }
        return externalServerDetailDTOList;
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

