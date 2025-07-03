package com.intimetec.newsportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Integer keywordId;

    @Column(name = "keyword", nullable = false, unique = true)
    private String keyword;

    public Keyword() {}

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Integer keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "keywordId=" + keywordId +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}

