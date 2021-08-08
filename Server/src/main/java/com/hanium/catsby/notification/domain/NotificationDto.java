package com.hanium.catsby.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDto {
    Long userId;
    String token;
}
