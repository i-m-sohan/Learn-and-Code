package com.intimetec.newsportal.console.dto;

public class ExternalServerStatusDTO {
    private String sourceName;
    private boolean status;
    private String lastAccessed;

    public ExternalServerStatusDTO() {}

    public ExternalServerStatusDTO(String sourceName, boolean status, String lastAccessed) {
        this.sourceName = sourceName;
        this.status = status;
        this.lastAccessed = lastAccessed;
    }

    public String getSourceName() { return sourceName; }
    public boolean getStatus() { return status; }
    public String getLastAccessed() { return lastAccessed; }

    public void setSourceName(String sourceName) { this.sourceName = sourceName; }
    public void setStatus(boolean status) { this.status = status; }
    public void setLastAccessed(String lastAccessed) { this.lastAccessed = lastAccessed; }
}
