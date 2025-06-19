package com.intimetec.newsportal.dto;

import java.time.LocalDateTime;

public class ExternalNewsSourceDTO {

    private Long sourceId;
    private String sourceName;
    private String apiKey;
    private String baseUrl; // ✅ Newly added field
    private Boolean status;
    private LocalDateTime lastAccessed;

    public ExternalNewsSourceDTO() {}

    public ExternalNewsSourceDTO(Long sourceId, String sourceName, String apiKey, String baseUrl, Boolean status, LocalDateTime lastAccessed) {
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.status = status;
        this.lastAccessed = lastAccessed;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    @Override
    public String toString() {
        return "ExternalNewsSourceDTO{" +
                "sourceId=" + sourceId +
                ", sourceName='" + sourceName + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", status=" + status +
                ", lastAccessed=" + lastAccessed +
                '}';
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }
}
