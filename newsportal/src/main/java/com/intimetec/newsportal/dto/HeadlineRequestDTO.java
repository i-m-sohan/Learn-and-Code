package com.intimetec.newsportal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HeadlineRequestDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String category;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
