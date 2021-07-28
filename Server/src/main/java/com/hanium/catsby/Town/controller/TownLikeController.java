package com.hanium.catsby.Town.controller;

import com.hanium.catsby.Town.domain.TownLike;
import com.hanium.catsby.Town.service.TownLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TownLikeController {

    @Autowired
    TownLikeService townService;

    @PostMapping("townCommunity/{id}/like")
    public String createTownLike(@PathVariable int id, @RequestBody TownLike townLike){//현재 유저의 정보도 넣어야 함
        townService.createTownLike(id, townLike);
        return "좋아요";
    }

    @DeleteMapping("townCommunity/{id}/like/{townLike_id}")
    public String deleteTownLike(@PathVariable int townLike_id){
        townService.deleteTownLike(townLike_id);
        return "좋아요 취소";
    }
}
