package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.CategoryNotificationPreferenceDTO;
import com.intimetec.newsportal.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsportal.intimetec.com/v1")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping("/category-preference/{userId}")
    public void setCategoryNotificationPreference(@PathVariable Long userId, @RequestBody CategoryNotificationPreferenceDTO categoryNotificationPreference){
        notificationService.updateUserCategoryPreference(userId,categoryNotificationPreference);
    }

    @GetMapping("/notification/{userId}")
    public ResponseEntity<?> getUserNotifications(@PathVariable Long userId){

    }
}
