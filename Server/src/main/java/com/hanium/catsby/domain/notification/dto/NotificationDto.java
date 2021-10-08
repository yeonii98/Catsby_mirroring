package com.hanium.catsby.domain.notification.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanium.catsby.domain.notification.model.Notification;
import com.hanium.catsby.util.NotificationUtil;
import lombok.Data;

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
