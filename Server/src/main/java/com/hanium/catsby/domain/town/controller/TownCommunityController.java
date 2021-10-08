package com.hanium.catsby.domain.town.controller;

import com.hanium.catsby.domain.common.sevice.S3Service;
import com.hanium.catsby.domain.town.model.TownCommunity;
import com.hanium.catsby.domain.town.repository.TownCommunityRepository;
import com.hanium.catsby.domain.town.service.TownCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class TownCommunityController {

    private final static String TOWN_COMMUNITY_DIR_NAME = "image/town/community/";

    private final TownCommunityService townCommunityService;
    private final TownCommunityRepository townCommunityRepository;
    private final S3Service s3Service;
    private TownCommunity townCommunity;

    //글목록
    @GetMapping("/townCommunity/{uid}")//커뮤니티 메인
    public List<TownCommunity> town(@PathVariable String uid) {
        return townCommunityService.listTownCommunity(uid);
    }


    //글 쓰기
    @PostMapping("townCommunity/write/{uid}")
    public TownCommunity writeTown(@RequestParam(value = "file") MultipartFile file, @RequestParam HashMap<String, RequestBody> request, @PathVariable String uid) {
        townCommunity = new TownCommunity();

        if(!Objects.equals(file.getOriginalFilename(), "empty")){
            String imgUrl = s3Service.upload(file, TOWN_COMMUNITY_DIR_NAME, uid);
            townCommunity.setImage(imgUrl);
        }

        String title = String.valueOf(request.get("title"));
        String content = String.valueOf(request.get("content"));
        String anonymous = String.valueOf(request.get("anonymous"));

        townCommunity.setTitle(title);
        townCommunity.setContent(content);
        if (anonymous.equals("true")) townCommunity.setAnonymous(true);
        else townCommunity.setAnonymous(false);


        return townCommunityService.writeTownCommunity(townCommunity, uid);
    }

    //글 삭제
    @DeleteMapping("townCommunity/{id}")
    public String deleteTown(@PathVariable int id) {
        townCommunityService.deleteTownCommunity(id);
        return "글 삭제하기";
    }

    //글 수정
    @PutMapping("townCommunity/{id}")
    public TownCommunity updateTown(@RequestParam(value = "file") MultipartFile file, @RequestParam HashMap<String, RequestBody> request, @PathVariable int id) {
        townCommunity = townCommunityRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 게시글 id를 찾을 수 없습니다.");
                });

        if(!Objects.equals(file.getOriginalFilename(), "empty") && !Objects.equals(file.getOriginalFilename(), "same")){
            String imgUrl = s3Service.upload(file, TOWN_COMMUNITY_DIR_NAME, String.valueOf(request.get("uid")));
            townCommunity.setImage(imgUrl);
        }
        else{
            townCommunity.setImage(townCommunity.getImage());
        }

        String title = String.valueOf(request.get("title"));
        String content = String.valueOf(request.get("content"));
        String anonymous = String.valueOf(request.get("anonymous"));

        townCommunity.setTitle(title);
        townCommunity.setContent(content);
        if (anonymous.equals("true")) townCommunity.setAnonymous(true);
        else townCommunity.setAnonymous(false);

        return townCommunityService.updateTownCommunity(id, townCommunity);
    }
}
