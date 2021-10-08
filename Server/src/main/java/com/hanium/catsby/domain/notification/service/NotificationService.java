package com.hanium.catsby.domain.notification.service;

import com.google.firebase.messaging.*;
import com.hanium.catsby.domain.bowl.model.Bowl;
import com.hanium.catsby.domain.bowl.repository.BowlRepository;
import com.hanium.catsby.domain.notification.dto.TokenDto;
import com.hanium.catsby.domain.notification.dto.NotificationDto;
import com.hanium.catsby.domain.user.model.Users;
import com.hanium.catsby.domain.user.repository.UserRepository;
import com.hanium.catsby.domain.notification.model.Notification;
import com.hanium.catsby.domain.notification.model.NotificationType;
import com.hanium.catsby.domain.notification.repository.NotificationRepository;
import com.hanium.catsby.util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final String TITLE = " 밥그릇에 급여 완료";
    private static final String BODY = "에 급여되었습니다.";

    private static final String MESSAGE1 = "님이";
    private static final String MESSAGE2 = "밥그릇에 급여했습니다.";


    private final UserRepository userRepository;
    private final BowlRepository bowlRepository;
    private final NotificationRepository notificationRepository;

    public void sendMessages(Long bowlId, String uid) throws FirebaseMessagingException {

        Bowl bowl = bowlRepository.findBowl(bowlId);
        String topic = bowl.getName();

        Users user = userRepository.findUserByUid(uid);
        String userToken = user.getFcmToken();

        List<TokenDto> users = bowlRepository.findUsersByBowlId(bowlId);
        List<String> registrationTokens = new ArrayList<>();

        for (TokenDto tokenDto : users) {
            String token = tokenDto.getToken();
            if (token.equals(userToken)) continue;

            registrationTokens.add(tokenDto.getToken());

            Users sendUser = userRepository.findUser(tokenDto.getUserId());
            saveNotification(sendUser, bowl.getName(), NotificationType.BOWL_USER);
        }

        String title = bowl.getName() + TITLE;
        String body = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분")) + BODY;

        MulticastMessage message = NotificationUtil.sendMulticastMessage(title, body, registrationTokens);

        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
        System.out.println(response.getSuccessCount() + " messages were sent successfully");
    }

    @Transactional
    public void saveNotification(Users user, String content, NotificationType type) {

        String message = user.getNickname() + NotificationUtil.makeNotification(content, type);

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    public List<NotificationDto> getNotificationList(String uid, int page) {

        Users user = userRepository.findUserByUid(uid);

        Page<Notification> notifications = notificationRepository
                .findByUserId(user.getId(), PageRequest.of(page, 20, Sort.Direction.DESC, "createdDate"));

        List<NotificationDto> notificationDto = notifications.stream()
                .map(NotificationDto::new)
                .collect(Collectors.toList());

        return notificationDto;
    }
}
