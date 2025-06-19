package com.intimetec.newsportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "keyword")
public class Keyword {

    @Id
    @Column(name = "keyword", nullable = false, unique = true)
    private String keyword;

    public Keyword() {}

    public Keyword(String keyword) {
        this.keyword = keyword;
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
                "keyword='" + keyword + '\'' +
                '}';
    }
}
