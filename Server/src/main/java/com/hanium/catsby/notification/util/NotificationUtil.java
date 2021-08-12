package com.hanium.catsby.notification.util;

import com.hanium.catsby.notification.domain.NotificationType;

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
}
