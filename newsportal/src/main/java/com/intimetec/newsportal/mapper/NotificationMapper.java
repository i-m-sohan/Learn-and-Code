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
        dto.setUserId(notification.getUser().getId());
        dto.setArticleId(notification.getArticle().getArticleId());
        dto.setMessage(notification.getMessage());
        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }

    public static Notification toEntity(NotificationDTO dto, User user) {
        if (dto == null) return null;

        Notification notification = new Notification();
        notification.setNotificationId(dto.getNotificationId());
        notification.setUser(user);
        notification.setMessage(dto.getMessage());
        notification.setCreatedAt(dto.getCreatedAt());
        return notification;
    }

    public static List<NotificationDTO> toDTOList(List<Notification> notificationList){

        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for(Notification notification : notificationList){
            NotificationDTO notificationDTO =  toDTO(notification);
            notificationDTOList.add(notificationDTO);
        }

        return notificationDTOList;
    }
}
