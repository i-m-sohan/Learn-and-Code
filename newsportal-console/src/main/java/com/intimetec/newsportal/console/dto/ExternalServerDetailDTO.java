package com.intimetec.newsportal.console.dto;

public class ExternalServerDetailDTO {
    private Long externalServerId;
    private String name;
    private String apiKey;

    public ExternalServerDetailDTO() {}

    public ExternalServerDetailDTO(Long externalServerId,String name, String apiKey) {
        this.externalServerId = externalServerId;
        this.name = name;
        this.apiKey = apiKey;
    }

    public String getName() { return name; }
    public String getApiKey() { return apiKey; }

    public void setName(String name) { this.name = name; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public Long getExternalServerId() {
        return externalServerId;
    }

    public void setExternalServerId(Long externalServerId) {
        this.externalServerId = externalServerId;
    }
}

