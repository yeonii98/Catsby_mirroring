package com.hanium.catsby.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hanium.catsby.util.BaseResponse;
import com.hanium.catsby.notification.domain.NotificationDto;
import com.hanium.catsby.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/send/{bowlId}/{uid}")
    public ResponseEntity<?> sendNotification(@PathVariable("bowlId") Long bowlId, @PathVariable("uid") String uid) {
        try {
            notificationService.sendMessages(bowlId, uid);
            return ResponseEntity.ok(new BaseResponse("success"));
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{uid}")
    public ResponseEntity<NotificationResponse> notifications(@PathVariable String uid, @RequestParam("page") int page) {
        return ResponseEntity.ok(new NotificationResponse(notificationService.getNotificationList(uid, page)));
    }

    @Data
    @AllArgsConstructor
    static class NotificationResponse {
        List<NotificationDto> notificationList;
    }
}
