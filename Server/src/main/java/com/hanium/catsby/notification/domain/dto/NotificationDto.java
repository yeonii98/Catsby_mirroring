package com.hanium.catsby.notification.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanium.catsby.notification.domain.Notification;
import com.hanium.catsby.util.NotificationUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private Long id;
    private Long userId;
    private String message;

    @JsonFormat(pattern="yyyy.MM.dd", timezone = "Asia/Seoul")
    private String date;

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUser().getId();
        this.message = notification.getMessage();
        this.date = NotificationUtil.getDateDifference(notification.getCreatedDate());
    }
}
