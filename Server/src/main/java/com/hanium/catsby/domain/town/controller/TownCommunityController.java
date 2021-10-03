package com.hanium.catsby.domain.town.controller;

import com.hanium.catsby.domain.town.model.TownCommunity;
import com.hanium.catsby.domain.town.service.TownCommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TownCommunityController {

    @Autowired
    TownCommunityService townCommunityService;

    //글목록
    @GetMapping("/townCommunity/{uid}")//커뮤니티 메인
    public List<TownCommunity> town(@PathVariable String uid) {
        return townCommunityService.listTownCommunity(uid);
    }

//    //글 조회
//    @GetMapping("/townCommunity/{id}")
//    public TownCommunity retrieveTown(@PathVariable int id) {
//        return townCommunityService.retrieveTownCommunity(id);
//    }

    //글 쓰기
    @PostMapping("townCommunity/write/{uid}")
    public String writeTown(@RequestBody TownCommunity townCommunity, @PathVariable String uid){
        townCommunityService.writeTownCommunity(townCommunity, uid);
        return "글 쓰기 ";
    }

    //글 삭제
    @DeleteMapping("townCommunity/{id}")
    public String deleteTown(@PathVariable int id){
        townCommunityService.deleteTownCommunity(id);
        return "글 삭제하기";
    }

    //글 수정
    @PutMapping("townCommunity/{id}")
    public String updateTown(@PathVariable int id, @RequestBody TownCommunity townCommunity){
        townCommunityService.updateTownCommunity(id,townCommunity);
        return "글 수정하기";
    }
}
