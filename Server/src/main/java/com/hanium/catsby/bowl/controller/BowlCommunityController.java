package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.BowlComment;
import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.service.BowlCommunityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BowlCommunityController {

    private final BowlCommunityService bowlCommunityService;

    @PostMapping("/bowl-community/write/{userId}")
    public CreateBowlCommunityResponse savaBowlCommunity(@PathVariable("userId") String userId, @RequestBody CreateBowlCommunityRequest request) {
        BowlCommunity bowlCommunity = new BowlCommunity();
        bowlCommunity.setImage(request.getImage());
        bowlCommunity.setContent(request.getContent());
        Long communityId = bowlCommunityService.savaCommunity(bowlCommunity, userId);
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

    @GetMapping("/bowl-communities/like/{communityId}")
    public Long bowlCommunityLikes(@PathVariable("communityId") Long communityId) {
        Long cnt = bowlCommunityService.findLikesByCommunity(communityId);
        return cnt;
    }

    /*
    @GetMapping("/bowl-communities/comment/{communityId}")
    public List<BowlComment> bowlCommunityComments(@PathVariable("communityId") Long communityId){
        List<>
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
