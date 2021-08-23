package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.service.BowlCommunityService;
import com.hanium.catsby.bowl.service.BowlService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BowlCommunityController {

    private final BowlCommunityService bowlCommunityService;
    private final BowlService bowlService;

    @PostMapping("/bowl-community/write/{bowlId}/{uid}")
    public CreateBowlCommunityResponse savaBowlCommunity(@PathVariable("bowlId") long bowlId, @PathVariable("uid") String uid, @RequestBody CreateBowlCommunityRequest request) {
        BowlCommunity bowlCommunity = new BowlCommunity();

        bowlCommunity.setImage(request.getImage());
        bowlCommunity.setContent(request.getContent());
        Long communityId = bowlCommunityService.savaCommunity(bowlCommunity, uid, bowlId);
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

    @GetMapping("/bowl-communities/{uid}")
    public List<BowlCommunity> bowlCommunities(@PathVariable("uid") String uid) {
        List<BowlCommunity> findCommunities = bowlCommunityService.findCommunitiesByUser(uid);
        return findCommunities;
    }

    /*오류 잡기*/
    @GetMapping("/bowl-communities/{bowlId}")
    public List<BowlCommunity> bowlCommunitiesByBowl(@PathVariable("bowlId") Long bowlId) {
        List<BowlCommunity> community = bowlCommunityService.findCommunityByBowl(bowlId);
        return community;
    }

    @GetMapping("/bowl-communities/like/{communityId}")
    public Long bowlCommunityLikes(@PathVariable("communityId") Long communityId) {
        Long cnt = bowlCommunityService.findLikesByCommunity(communityId);
        return cnt;
    }

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
        bowlCommunityService.update(communityId, request.getContent());
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
