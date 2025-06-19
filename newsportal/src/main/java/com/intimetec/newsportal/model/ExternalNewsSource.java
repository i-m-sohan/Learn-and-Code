package com.intimetec.newsportal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ExternalNewsSource")
public class ExternalNewsSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sourceId")
    private Long sourceId;

    @Column(name = "sourceName", nullable = false)
    private String sourceName;

    @Column(name = "baseUrl", nullable = false)
    private String baseUrl;

    @Column(name = "apiKey", nullable = false)
    private String apiKey;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "lastAccessed")
    private LocalDateTime lastAccessed;

    public ExternalNewsSource() {}

    public ExternalNewsSource(String sourceName, String apiKey, Boolean status) {
        this.sourceName = sourceName;
        this.apiKey = apiKey;
        this.status = status;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    @Override
    public String toString() {
        return "ExternalNewsSource{" +
                "sourceId=" + sourceId +
                ", sourceName='" + sourceName + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", status=" + status +
                ", lastAccessed=" + lastAccessed +
                '}';
    }
}

