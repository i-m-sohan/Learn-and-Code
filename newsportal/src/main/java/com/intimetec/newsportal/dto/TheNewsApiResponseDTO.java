package com.intimetec.newsportal.dto;

import java.util.List;

public class TheNewsApiResponseDTO {

    private Meta meta;
    private List<TheNewsApiArticleDTO> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<TheNewsApiArticleDTO> getData() {
        return data;
    }

    public void setData(List<TheNewsApiArticleDTO> data) {
        this.data = data;
    }

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
}
