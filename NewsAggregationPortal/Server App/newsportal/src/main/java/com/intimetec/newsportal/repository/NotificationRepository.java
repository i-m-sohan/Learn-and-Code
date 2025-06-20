package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
