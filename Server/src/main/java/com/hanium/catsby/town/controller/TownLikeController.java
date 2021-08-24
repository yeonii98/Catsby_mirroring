package com.hanium.catsby.Town.controller;

import com.hanium.catsby.Town.domain.TownLike;
import com.hanium.catsby.Town.service.TownLikeService;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.service.NotificationService;
import com.hanium.catsby.util.NotificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TownLikeController {

    @Autowired
    TownLikeService townLikeService;
    NotificationService notificationService;

    @PostMapping("townCommunity/{id}/like")
    public String createTownLike(@PathVariable int id, @RequestBody TownLike townLike){//현재 유저의 정보도 넣어야 함
        townLikeService.createTownLike(id, townLike);

//        String content = townLike.getTownCommunity().getTitle();
//        String message = id + NotificationUtil.makeNotification(content, NotificationType.LIKE);
//        notificationService.saveNotification(townLike.getTownCommunity().getUser(), message);

        return "좋아요";
    }

    @DeleteMapping("townCommunity/{id}/like")
    public String deleteTownLike(@PathVariable int id){
        townLikeService.deleteTownLike(id, 2);
        return "좋아요 취소";
    }

    @GetMapping("townCommunity/{id}/like/{user_id}")
    public int getLike(@PathVariable int id, @PathVariable int user_id){
        return townLikeService.isPresent(id,user_id);
    }
}
