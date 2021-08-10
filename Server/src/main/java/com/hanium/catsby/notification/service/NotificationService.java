package com.hanium.catsby.notification.service;

import com.google.firebase.messaging.*;
import com.hanium.catsby.bowl.domain.Bowl;
import com.hanium.catsby.bowl.repository.BowlRepository;
import com.hanium.catsby.notification.domain.NotificationDto;
import com.hanium.catsby.user.domain.Users;
import com.hanium.catsby.user.repository.UserRepository;
import com.hanium.catsby.notification.domain.Notification;
import com.hanium.catsby.notification.domain.TokenDto;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.repository.NotificationRepository;
import com.hanium.catsby.notification.util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final String TITLE = "급여 완료";
    private static final String BODY = "에 급여되었습니다.";

    private static final String MESSAGE1 = "님이";
    private static final String MESSAGE2 = "밥그릇에 급여했습니다.";


    private final UserRepository userRepository;
    private final BowlRepository bowlRepository;
    private final NotificationRepository notificationRepository;

    public void sendMessages(Long bowlId, Long userId) throws FirebaseMessagingException {

        Bowl bowl = bowlRepository.findBowl(bowlId);
        String topic = bowl.getName();

        Users user = userRepository.findUser(userId);
        String userToken = user.getFcmToken();

        List<TokenDto> users = bowlRepository.findUsersByBowlId(bowlId);
        List<String> registrationTokens = new ArrayList<>();

        String saveMessage = userId + NotificationUtil.makeNotification(bowl.getName(), NotificationType.BOWL_USER);

        for (TokenDto tokenDto : users) {
            String token = tokenDto.getToken();
            if (token.equals(userToken)) continue;

            registrationTokens.add(tokenDto.getToken());

            Users sendUser = userRepository.findUser(tokenDto.getUserId());
            saveNotification(sendUser, saveMessage);
        }

        String title = bowl.getName() + TITLE;
        String body = LocalDateTime.now() + BODY;

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

        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
        System.out.println(response.getSuccessCount() + " messages were sent successfully");
    }

    @Transactional
    public void saveNotification(Users user, String message) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    public List<NotificationDto> getNotificationList(Long userId, int page) {

        Page<Notification> notifications = notificationRepository
                .findByUserId(userId, PageRequest.of(page, 20, Sort.Direction.ASC, "createDate"));

        List<NotificationDto> notificationDto = notifications.stream()
                .map(NotificationDto::new)
                .collect(Collectors.toList());

        return notificationDto;
    }
}
