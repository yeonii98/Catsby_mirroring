package com.hanium.catsby.TownLike.controller;

import com.hanium.catsby.TownLike.service.TownLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TownLikeController {

    @Autowired
    TownLikeService townService;

    @PostMapping("townCommunity/{townCommunity_id}/like")
    public String createTownLike(@PathVariable int townCommunity_id, @RequestBody TownLike townLike){//현재 유저의 정보도 넣어야 함
        townService.createTownLike(townCommunity_id, townLike);
        return "좋아요";
    }

    @DeleteMapping("townCommunity/{townCommunity_id}/like/{townLike_id}")
    public String deleteTownLike(@PathVariable int townLike_id){
        townService.deleteTownLike(townLike_id);
        return "좋아요 취소";
    }
}
