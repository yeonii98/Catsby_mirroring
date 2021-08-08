package com.hanium.catsby.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hanium.catsby.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
