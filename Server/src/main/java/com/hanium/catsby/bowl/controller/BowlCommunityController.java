package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.service.BowlCommunityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BowlCommunityController {

    private final BowlCommunityService bowlCommunityService;

    @PostMapping("/bowl-community/write/{userId}/{bowlId}")
    public CreateBowlCommunityResponse savaBowlCommunity(@PathVariable("userId") Long userId, @PathVariable("bowlId") Long bowlId, @RequestBody CreateBowlCommunityRequest request) {
        BowlCommunity bowlCommunity = new BowlCommunity();
        bowlCommunity.setImage(request.getImage());
        bowlCommunity.setContent(request.getContent());
        Long communityId = bowlCommunityService.savaCommunity(bowlCommunity, userId, bowlId);
        return new CreateBowlCommunityResponse(communityId);
    }

    @Data
    static class CreateBowlCommunityRequest{
        private byte[] image;
        private String content;
    }

    @Data
    static class CreateBowlCommunityResponse{
        private Long id;

        public CreateBowlCommunityResponse(Long id) {
            this.id = id;
        }
    }

    @GetMapping("/bowl-communities")
    public List<BowlCommunity> bowlCommunities() {
        return bowlCommunityService.findCommunities();
    }
    /*
    public BowlCommunityResult bowlCommunities() {
        List<BowlCommunity> findcommunities = bowlCommunityService.findCommunities();
        List<BowlCommunityDto> collect = findcommunities.stream().map(f -> new BowlCommunityDto(f.getImage(), f.getContent(), f.getCreateDate()))
                .collect(Collectors.toList());
        return new BowlCommunityResult(collect);

    }*/

    @Data
    @AllArgsConstructor
    static class BowlCommunityResult<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class BowlCommunityDto{
        private byte[] image;
        private String content;
        private LocalDateTime createDate;
    }

    @PutMapping("/bowl-community/{communityId}")
    public UpdateBowlCommunityResponse updateBowlCommunity(@PathVariable("communityId") Long communityId, @RequestBody UpdateBowlCommunityRequest request){
        bowlCommunityService.update(communityId, request.getImage(), request.getContent());
        BowlCommunity findBowlCommunity = bowlCommunityService.findCommunity(communityId);
        return new UpdateBowlCommunityResponse(findBowlCommunity.getId(), findBowlCommunity.getImage(), findBowlCommunity.getContent());
    }


    @DeleteMapping("/bowl-community/{communityId}")
    public void DeleteBowlCommunity(@PathVariable("communityId") Long id){
        bowlCommunityService.delete(id);
    }

    @Data
    static class UpdateBowlCommunityRequest{
        private Long id;
        private byte[] image;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class UpdateBowlCommunityResponse{
        private Long id;
        private byte[] image;
        private String content;
    }
}
