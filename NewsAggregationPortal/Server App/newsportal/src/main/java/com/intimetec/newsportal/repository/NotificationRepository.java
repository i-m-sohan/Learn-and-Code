package com.intimetec.newsportal.repository;

import com.intimetec.newsportal.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUser_Id(Long userId);
}
