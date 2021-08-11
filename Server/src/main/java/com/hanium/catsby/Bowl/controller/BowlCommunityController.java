package com.hanium.catsby.Bowl.controller;

import com.hanium.catsby.Bowl.domain.BowlCommunity;
import com.hanium.catsby.Bowl.service.BowlCommunityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BowlCommunityController {

    private final BowlCommunityService bowlCommunityService;

    @PostMapping("/bowl-community/write/{userId}/{bowlId}")
    public CreateBowlCommunityResponse savaBowlCommunity(@PathVariable("userId") Long userId, @PathVariable("bowlId") Long bowlId, @RequestBody BowlCommunity bowlCommunity) {
        Long communityId = bowlCommunityService.savaCommunity(bowlCommunity, userId, bowlId);
        return new CreateBowlCommunityResponse(communityId);
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
