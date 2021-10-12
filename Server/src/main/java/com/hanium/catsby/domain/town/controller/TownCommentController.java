package com.hanium.catsby.domain.town.controller;

import com.hanium.catsby.domain.town.model.TownCommunity;
import com.hanium.catsby.domain.town.service.TownCommentService;
import com.hanium.catsby.domain.town.model.TownComment;
import com.hanium.catsby.domain.notification.model.NotificationType;
import com.hanium.catsby.domain.notification.service.NotificationService;
import com.hanium.catsby.domain.user.model.Users;
import com.hanium.catsby.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TownCommentController {
    @Autowired
    TownCommentService townCommentService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @GetMapping("townCommunity/{id}/comment")
    public List townCommunity(@PathVariable int id){
        return townCommentService.listTownComment(id);
    }

    //댓글 쓰기
    @PostMapping("townCommunity/{id}/comment/{uid}")
    public TownComment writeTownComment(@PathVariable int id, @PathVariable String uid, @RequestBody TownComment townComment){//현재 유저의 정보도 넣어야 함
        Users user = userService.findUsersByUid(uid);

        TownComment mTownComment = townCommentService.writeTownComment(id, uid, townComment);

        TownCommunity townCommunity = townComment.getTownCommunity();
        notificationService.saveNotification(user, townCommunity.getUser(), townComment.getContent(), NotificationType.COMMENT);

        return mTownComment;
    }

    //댓글 삭제하기
    @DeleteMapping("townComment/{townComment_id}")
    public String deleteTownComment(@PathVariable int townComment_id){
        townCommentService.deleteTownComment(townComment_id);
        return "댓글 삭제하기";
    }

    //댓글 수정하기
    @PutMapping("townComment/{townComment_id}")
    public String updateTownComment( @PathVariable int townComment_id, @RequestBody TownComment townComment){
        townCommentService.updateTownComment(townComment_id,townComment);
        return "댓글 수정하기";
    }
}
