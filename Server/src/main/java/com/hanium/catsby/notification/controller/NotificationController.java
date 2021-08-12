package com.hanium.catsby.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hanium.catsby.notification.domain.Notification;
import com.hanium.catsby.notification.domain.NotificationDto;
import com.hanium.catsby.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/send/{bowlId}/{userId}")
    public ResponseEntity<?> sendNotification(@PathVariable("bowlId") Long bowlId, @PathVariable("userId") Long userId) {
        try {
            notificationService.sendMessages(bowlId, userId);
            return ResponseEntity.ok().build();
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<NotificationResponse> notifications(@PathVariable Long userId, @RequestParam("page") int page) {
        return ResponseEntity.ok(new NotificationResponse(notificationService.getNotificationList(userId, page)));
    }

    @Data
    @AllArgsConstructor
    static class NotificationResponse {
        List<NotificationDto> notificationList;
    }
}
