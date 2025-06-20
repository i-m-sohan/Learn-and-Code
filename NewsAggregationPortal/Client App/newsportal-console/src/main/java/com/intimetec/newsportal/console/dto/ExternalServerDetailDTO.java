package com.intimetec.newsportal.console.dto;

public class ExternalServerDetailDTO {
    private String name;
    private String apiKey;

    public ExternalServerDetailDTO() {}

    public ExternalServerDetailDTO(String name, String apiKey) {
        this.name = name;
        this.apiKey = apiKey;
    }

    public String getName() { return name; }
    public String getApiKey() { return apiKey; }

    public void setName(String name) { this.name = name; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
}

