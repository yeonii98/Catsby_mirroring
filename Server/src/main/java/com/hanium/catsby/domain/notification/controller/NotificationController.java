package com.hanium.catsby.domain.notification.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.hanium.catsby.domain.bowl.service.BowlService;
import com.hanium.catsby.domain.notification.service.NotificationService;
import com.hanium.catsby.domain.common.dto.BaseResponse;
import com.hanium.catsby.domain.notification.dto.NotificationDto;
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
    private final BowlService bowlService;

    @GetMapping("/send/{bowlId}/{uid}")
    public ResponseEntity<?> sendNotification(@PathVariable("bowlId") Long bowlId, @PathVariable("uid") String uid) {
        try {
            bowlService.saveBowlFeed(uid, bowlId);
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
