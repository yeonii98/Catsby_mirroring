package com.hanium.catsby.town.controller;

import com.hanium.catsby.town.domain.TownLike;
import com.hanium.catsby.town.service.TownLikeService;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.service.NotificationService;
import com.hanium.catsby.notification.util.NotificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TownLikeController {

    @Autowired
    TownLikeService townService;
    NotificationService notificationService;

    @PostMapping("townCommunity/{id}/like")
    public String createTownLike(@PathVariable int id, @RequestBody TownLike townLike){//현재 유저의 정보도 넣어야 함
        townService.createTownLike(id, townLike);

        String content = townLike.getTownCommunity().getTitle();
        String message = id + NotificationUtil.makeNotification(content, NotificationType.LIKE);
        notificationService.saveNotification(townLike.getTownCommunity().getUser(), message);

        return "좋아요";
    }

    @DeleteMapping("townCommunity/{id}/like/{townLike_id}")
    public String deleteTownLike(@PathVariable int townLike_id){
        townService.deleteTownLike(townLike_id);
        return "좋아요 취소";
    }
}
