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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Boolean notificationsEnabled = true;

    public UserCategoryPreference() {}

    public UserCategoryPreference(User user, Category category, Boolean notificationsEnabled) {
        this.user = user;
        this.category = category;
        this.notificationsEnabled = notificationsEnabled;
    }

    public Long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(Long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
                ", user=" + (user != null ? user.getId() : null) +
                ", category=" + (category != null ? category.getCategoryId() : null) +
                ", notificationsEnabled=" + notificationsEnabled +
                '}';
    }
}
