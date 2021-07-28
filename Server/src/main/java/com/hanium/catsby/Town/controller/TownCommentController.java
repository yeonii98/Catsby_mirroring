package com.hanium.catsby.TownComment.controller;

import com.hanium.catsby.TownComment.service.TownCommentService;
import com.hanium.catsby.domain.TownComment;
import com.hanium.catsby.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TownCommentController {
    @Autowired
    TownCommentService townService;

    @PostMapping("townCommunity/{townCommunity_id}/comment")
    public String writeTownComment(@PathVariable int townCommunity_id, @RequestBody TownComment townComment){//현재 유저의 정보도 넣어야 함
        townService.writeTownComment(townCommunity_id, townComment);
        return "댓글 쓰기";
    }

    @DeleteMapping("townCommunity/{townCommunity_id}/comment/{townComment_id}")
    public String deleteTownComment(@PathVariable int townComment_id){
        townService.deleteTownComment(townComment_id);
        return "댓글 삭제하기";
    }
}
