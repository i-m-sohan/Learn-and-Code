package com.intimetec.newsportal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public class NewsApiResponseDTO {

    private Meta meta;
    private List<Article> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }

    // --- Inner Class: Meta ---

    public static class Meta {
        private long found;
        private int returned;
        private int limit;
        private int page;

        public long getFound() {
            return found;
        }

        public void setFound(long found) {
            this.found = found;
        }

        public int getReturned() {
            return returned;
        }

        public void setReturned(int returned) {
            this.returned = returned;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    // --- Inner Class: Article ---

    public static class Article {
        private String uuid;
        private String title;
        private String description;
        private String keywords;
        private String snippet;
        private String url;

        @JsonProperty("image_url")
        private String imageUrl;

        private String language;

        @JsonProperty("published_at")
        private OffsetDateTime publishedAt;

        private String source;
        private List<String> categories;

        @JsonProperty("relevance_score")
        private Double relevanceScore;

        // Getters and Setters

        public String getUuid() { return uuid; }
        public void setUuid(String uuid) { this.uuid = uuid; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getKeywords() { return keywords; }
        public void setKeywords(String keywords) { this.keywords = keywords; }

        public String getSnippet() { return snippet; }
        public void setSnippet(String snippet) { this.snippet = snippet; }

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }

        public OffsetDateTime getPublishedAt() { return publishedAt; }
        public void setPublishedAt(OffsetDateTime publishedAt) { this.publishedAt = publishedAt; }

        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }

        public List<String> getCategories() { return categories; }
        public void setCategories(List<String> categories) { this.categories = categories; }

        public Double getRelevanceScore() { return relevanceScore; }
        public void setRelevanceScore(Double relevanceScore) { this.relevanceScore = relevanceScore; }
    }
}
