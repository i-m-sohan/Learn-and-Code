package com.intimetec.newsportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UserCategoryKeyword", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"preferenceId", "keyword"})
})
public class UserCategoryKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "preferenceId", nullable = false)
    private UserCategoryPreference preference;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "keyword", referencedColumnName = "keyword", nullable = false)
    private Keyword keyword;

    public UserCategoryKeyword() {}

    public UserCategoryKeyword(UserCategoryPreference preference, Keyword keyword) {
        this.preference = preference;
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public UserCategoryPreference getPreference() {
        return preference;
    }

    public void setPreference(UserCategoryPreference preference) {
        this.preference = preference;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserCategoryKeyword{" +
                "id=" + id +
                ", preferenceId=" + (preference != null ? preference.getPreferenceId() : null) +
                ", keyword=" + (keyword != null ? keyword.getKeyword() : null) +
                '}';
    }
}
