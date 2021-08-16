package com.hanium.catsby.town.controller;

import com.hanium.catsby.town.service.TownCommunityService;
import com.hanium.catsby.town.domain.TownCommunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TownCommunityController {

    @Autowired
    TownCommunityService townCommunityService;

    //글목록
    @GetMapping("/townCommunity")//커뮤니티 메인
    public List town() {
        return townCommunityService.listTownCommunity();
    }

    //글 조회
    @GetMapping("/townCommunity/{id}")
    public TownCommunity retrieveTown(@PathVariable int id) {
        return townCommunityService.retrieveTownCommunity(id);
    }

    //글 쓰기
    @PostMapping("townCommunity/write")
    public String writeTown(@RequestBody TownCommunity townCommunity){//현재 유저의 정보도 넣어야 함
        townCommunityService.writeTownCommunity(townCommunity);
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
