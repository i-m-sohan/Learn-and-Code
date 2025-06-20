package com.intimetec.newsportal.console.dto;

public class ExternalServerUpdateDTO {
    private Long serverID;
    private String apiKey;

    public Long getServerID() {
        return serverID;
    }

    public void setServerID(Long serverID) {
        this.serverID = serverID;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
