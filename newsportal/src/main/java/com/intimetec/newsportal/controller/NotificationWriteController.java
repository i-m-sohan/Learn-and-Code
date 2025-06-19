package com.intimetec.newsportal.controller;

import com.intimetec.newsportal.dto.KeywordConfigDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NotificationWriteController {
    public void addNotificationKeywords(@RequestBody KeywordConfigDTO keywordConfigDTO){

    }
}
