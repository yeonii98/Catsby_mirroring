package com.hanium.catsby.town.controller;

import com.hanium.catsby.town.service.TownCommentService;
import com.hanium.catsby.town.domain.TownComment;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.service.NotificationService;
import com.hanium.catsby.notification.util.NotificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TownCommentController {
    @Autowired
    TownCommentService townCommentService;
    NotificationService notificationService;

    //댓글 쓰기
    @PostMapping("townCommunity/{id}/comment")
    public String writeTownComment(@PathVariable int id, @RequestBody TownComment townComment){//현재 유저의 정보도 넣어야 함
        townCommentService.writeTownComment(id, townComment);

        String content = townComment.getTownCommunity().getTitle();
        String message = id + NotificationUtil.makeNotification(content, NotificationType.COMMENT);
        notificationService.saveNotification(townComment.getTownCommunity().getUser(), message);

        return "댓글 쓰기";
    }

    //댓글 삭제하기
    @DeleteMapping("townCommunity/{id}/comment/{townComment_id}")
    public String deleteTownComment(@PathVariable int townComment_id){
        townCommentService.deleteTownComment(townComment_id);
        return "댓글 삭제하기";
    }

    //댓글 수정하기
    @PutMapping("townCommunity/{id}/comment/{townComment_id}")
    public String updateTownComment( @PathVariable int townComment_id, @RequestBody TownComment townComment){
        townCommentService.updateTownComment(townComment_id,townComment);
        return "댓글 수정하기";
    }
}
