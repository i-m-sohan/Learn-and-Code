package com.intimetec.newsportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "userCategoryPreference", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "categoryId"})
})
public class UserCategoryPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    @Column(nullable = false)
    private Long userId; // Matches bigint

    @Column(nullable = false)
    private Integer categoryId; // Matches int

    @Column(nullable = false)
    private Boolean notificationsEnabled = true; // Matches tinyint(1)

    public UserCategoryPreference() {
    }

    public UserCategoryPreference(Long userId, Integer categoryId, Boolean notificationsEnabled) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.notificationsEnabled = notificationsEnabled;
    }

    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    @Override
    public String toString() {
        return "UserCategoryPreference{" +
                "preferenceId=" + preferenceId +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", notificationsEnabled=" + notificationsEnabled +
                '}';
    }
}




