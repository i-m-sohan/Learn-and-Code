package com.intimetec.newsportal.mapper;


import com.intimetec.newsportal.dto.NotificationDTO;
import com.intimetec.newsportal.model.Notification;
import com.intimetec.newsportal.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification) {
        if (notification == null) return null;

        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(notification.getNotificationId());
        dto.setUserId(notification.getUserId());
        dto.setArticleId(notification.getArticleId());
        dto.setMessage(notification.getMessage());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }

    public static Notification toEntity(NotificationDTO dto) {
        if (dto == null) return null;

        Notification notification = new Notification();
        notification.setNotificationId(dto.getNotificationId());
        notification.setUserId(dto.getUserId());
        notification.setArticleId(dto.getArticleId());
        notification.setMessage(dto.getMessage());
        notification.setCreatedAt(dto.getCreatedAt());
        return notification;
    }

    public static List<NotificationDTO> toDTOList(List<Notification> notificationList) {
        List<NotificationDTO> dtoList = new ArrayList<>();
        for (Notification notification : notificationList) {
            dtoList.add(toDTO(notification));
        }
        return dtoList;
    }
}

