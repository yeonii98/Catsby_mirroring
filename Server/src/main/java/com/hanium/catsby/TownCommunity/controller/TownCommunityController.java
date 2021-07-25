package com.hanium.catsby.TownCommunity.controller;

import com.hanium.catsby.TownCommunity.service.TownCommunityService;
import com.hanium.catsby.domain.TownCommunity;
import com.hanium.catsby.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TownCommunityController {

    @Autowired
    TownCommunityService townService;

    @GetMapping("/townCommunity")//커뮤니티 메인
    public List town() {
        return townService.listTownCommunity();
    }

    @GetMapping("/townCommunity/{townCommunity_id}")
    public com.hanium.catsby.domain.TownCommunity retrieveTown(@PathVariable int townCommunity_id) {
        return townService.retrieveTownCommunity(townCommunity_id);
    }

    @PostMapping("townCommunity/write")
    public String writeTown(@RequestBody com.hanium.catsby.domain.TownCommunity townCommunity){//현재 유저의 정보도 넣어야 함
        townService.writeTownCommunity(townCommunity);
        return "글 쓰기";
    }

    @DeleteMapping("townCommunity/{townCommunity_id}")
    public String deleteTown(@PathVariable int townCommunity_id){
        townService.deleteTownCommunity(townCommunity_id);
        return "글 삭제하기";
    }

    @PutMapping("townCommunity/{townCommunity_id}")
    public String updateTown(@PathVariable int townCommunity_id, @RequestBody TownCommunity townCommunity){
        townService.updateTownCommunity(townCommunity_id,townCommunity);
        return "글 수정하기";
    }
}
