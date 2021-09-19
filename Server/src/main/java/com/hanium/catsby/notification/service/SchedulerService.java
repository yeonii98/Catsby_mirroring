package com.hanium.catsby.notification.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.hanium.catsby.bowl.domain.Bowl;
import com.hanium.catsby.bowl.repository.BowlRepository;
import com.hanium.catsby.notification.domain.dto.TokenDto;
import com.hanium.catsby.util.NotificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private static final String TITLE = " 밥그릇 급여 알림";
    private static final String BODY = "일 동안에 급여되지 않았습니다.";

    private final BowlRepository bowlRepository;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void sendNotification() throws FirebaseMessagingException {
        System.out.println("scheduler execute");

        List<Bowl> bowls = bowlRepository.findBowlsByLastFeeding();

        for (Bowl bowl : bowls) {
            List<TokenDto> users = bowlRepository.findUsersByBowlId(bowl.getId());
            List<String> registrationTokens = new ArrayList<>();

            if (users.isEmpty())
                continue;

            for (TokenDto token: users) {
                registrationTokens.add(token.getToken());
            }

            LocalDateTime lastFeeding = bowl.getLastFeeding();

            String title = bowl.getName() + TITLE;
            String body;
            if (lastFeeding == null) {
                body = "아직 한 번도 급여되지 않았습니다.";
            } else {
                body = ChronoUnit.DAYS.between(lastFeeding, LocalDateTime.now()) + BODY;
            }

            MulticastMessage message = NotificationUtil.sendMulticastMessage(title, body, registrationTokens);

            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            System.out.println(response.getSuccessCount() + " messages were sent successfully");

        }

    }
}
