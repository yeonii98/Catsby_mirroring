package com.hanium.catsby.town.controller;

import com.hanium.catsby.town.domain.TownLike;
import com.hanium.catsby.town.service.TownLikeService;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.service.NotificationService;
import com.hanium.catsby.user.domain.Users;
import com.hanium.catsby.user.service.UserService;
import com.hanium.catsby.util.NotificationUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TownLikeController {

    @Autowired
    TownLikeService townLikeService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @PostMapping("townCommunity/{id}/like/{uid}")
    public String createTownLike(@PathVariable int id, @PathVariable String uid, @RequestBody TownLike townLike){
        townLikeService.createTownLike(id, uid, townLike);

        Users user = userService.findUsersByUid(uid);
        String content = townLike.getTownCommunity().getTitle();
        notificationService.saveNotification(user, content, NotificationType.LIKE);

        return "좋아요 완료";
    }

    @DeleteMapping("townCommunity/{id}/like/{uid}")
    public String deleteTownLike(@PathVariable int id, @PathVariable String uid){
        townLikeService.deleteTownLike(id, uid);
        return "좋아요 취소";
    }

    @GetMapping("townCommunity/{id}/like/{uid}")
    public int getLike(@PathVariable int id, @PathVariable String uid){
        return townLikeService.isPresent(id, uid);
    }
}
