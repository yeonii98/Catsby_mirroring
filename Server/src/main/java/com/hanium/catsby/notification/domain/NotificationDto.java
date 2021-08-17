package com.hanium.catsby.notification.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private Long id;
    private Long userId;
    private String message;

    @JsonFormat(pattern="yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime date;

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.userId = notification.getUser().getId();
        this.message = notification.getMessage();
        this.date = notification.getCreatedDate();
    }
}
