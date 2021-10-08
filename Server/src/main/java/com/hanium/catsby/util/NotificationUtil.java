package com.hanium.catsby.util;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.MulticastMessage;
import com.hanium.catsby.domain.notification.model.NotificationType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NotificationUtil {

    public static String makeNotification(String content, NotificationType type) {

        String subTitle;
        if (content.length() > 16) {
            subTitle = content.substring(0, 16);
        } else {
            subTitle = content;
        }
        String message = null;
        if (type == NotificationType.COMMENT) {
            message = "님이 \"" + subTitle + "...\" 글에 댓글을 남겼습니다.";
        } else if (type == NotificationType.LIKE) {
            message = "님이 \"" + subTitle + "...\" 글에 좋아요를 눌렀습니다.";
        } else if (type == NotificationType.BOWL_USER) {
            message = "님이 \"" + content + "\" 밥그릇에 급여했습니다.";
        }
        return message;
    }

    public static String getDateDifference(LocalDateTime notificationTime) {

        LocalDateTime today = LocalDateTime.now();

        long years = ChronoUnit.YEARS.between(notificationTime, today);
        long months = ChronoUnit.MONTHS.between(notificationTime, today);
        long days = ChronoUnit.DAYS.between(notificationTime, today);
        long hours = ChronoUnit.HOURS.between(notificationTime, today);
        long minutes = ChronoUnit.MINUTES.between(notificationTime, today);
        long seconds = ChronoUnit.SECONDS.between(notificationTime, today);

        if (years == 0 && months == 0 && days == 0 && hours == 0 && minutes == 0) {
            return seconds + "초 전";
        } else if (years == 0 && months == 0 && days == 0 && hours == 0) {
            return minutes + "분 전";
        } else if (years == 0 && months == 0 && days == 0) {
            return hours + "시간 전";
        } else if (years == 0 && months == 0) {
            return days + "일 전";
        } else if (years == 0) {
            return months + "달 전";
        }


        return notificationTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public static MulticastMessage sendMulticastMessage(String title, String body, List<String> registrationTokens) {
        MulticastMessage message = MulticastMessage.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3600 * 1000)
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .setNotification(AndroidNotification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .setIcon("stock_ticker_update")
                                .setColor("#ffffff")
                                .build())
                        .build())
                .addAllTokens(registrationTokens)
                .build();

        return message;
    }
}
