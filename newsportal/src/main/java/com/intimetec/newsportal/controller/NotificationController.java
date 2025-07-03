package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.CategoryKeywordDTO;
import com.intimetec.newsportal.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.dto.NotificationDTO;
import com.intimetec.newsportal.dto.UserCategoryKeywordDTO;
import com.intimetec.newsportal.model.Notification;
import com.intimetec.newsportal.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/newsportal.intimetec.com/v1")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PatchMapping("/category-preference/{userId}")
    public ResponseEntity<?> setCategoryNotificationPreference(@PathVariable Long userId, @RequestBody CategoryNotificationPreferenceDTO categoryNotificationPreference){
        notificationService.updateUserCategoryPreference(userId,categoryNotificationPreference);
        return new ResponseEntity<>(Map.of("Message","Notification preference updated for the category"),HttpStatus.OK);
    }

    @GetMapping("/notification/{userId}")
    public ResponseEntity<?> getUserNotifications(@PathVariable Long userId){
        List<NotificationDTO> notificationDTOList = notificationService.getUserNotifications(userId);
        return new ResponseEntity<>(notificationDTOList, HttpStatus.OK);
    }
}
