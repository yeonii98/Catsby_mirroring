package com.hanium.catsby.bowl.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.service.BowlCommunityService;
import com.hanium.catsby.bowl.service.BowlService;
import com.hanium.catsby.user.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BowlCommunityController {

    private final BowlCommunityService bowlCommunityService;
    private final BowlService bowlService;

    @PostMapping("/bowl/community/write/{bowlId}/{uid}")
    public CreateBowlCommunityResponse savaBowlCommunity(@RequestParam(value = "file") MultipartFile file, @PathVariable("bowlId") long bowlId, @PathVariable("uid") String uid, @RequestParam HashMap<String, RequestBody> request ) throws IOException {
        BowlCommunity bowlCommunity = new BowlCommunity();
        bowlCommunity.setUid(uid);
        bowlCommunity.setImage(file.getBytes());
        String con = String.valueOf(request.get("content"));
        bowlCommunity.setContent(con);

        Long communityId = bowlCommunityService.savaCommunity(bowlCommunity, uid, bowlId);
        return new CreateBowlCommunityResponse(communityId);
    }

    @Data
    static class CreateBowlCommunityRequest{
        private String content;
        private String path;
    }

    @Data
    static class CreateBowlCommunityResponse{
        private Long id;
        public CreateBowlCommunityResponse(Long id) {
            this.id = id;
        }
    }

    @GetMapping("/bowl/communities/{bowlId}")
    public BowlCommunityResult bowlCommunitiesByBowl(@PathVariable("bowlId") Long bowlId) {
        List<BowlCommunity> community = bowlCommunityService.findCommunityByBowl(bowlId);
        List<BowlCommunityDto> collect = community.stream()
                .map(bc -> new BowlCommunityDto(bc.getId(), bc.getUser(), bc.getImage(), bc.getContent(), bc.getUid(), bc.getCreatedDate(), (long) bc.getLikeCount()))
                .collect(Collectors.toList());
        return new BowlCommunityResult(collect);
    }

    @Data
    @AllArgsConstructor
    static class BowlCommunityResult<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class BowlCommunityDto{
        private Long id;
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        private Users user;
        private byte[] image;
        private String content;
        private String uid;
        private LocalDateTime createdDate;
        private Long likeCount;
    }

    @PutMapping("/bowl/community/{communityId}")
    public UpdateBowlCommunityResponse updateBowlCommunity(@PathVariable("communityId") Long communityId, @RequestBody UpdateBowlCommunityRequest request){
        bowlCommunityService.update(communityId, request.getContent());
        BowlCommunity findBowlCommunity = bowlCommunityService.findCommunity(communityId);
        return new UpdateBowlCommunityResponse(findBowlCommunity.getId(), findBowlCommunity.getContent());
    }


    @DeleteMapping("/bowl/community/{communityId}")
    public void DeleteBowlCommunity(@PathVariable("communityId") Long id){
        bowlCommunityService.delete(id);
    }

    @Data
    static class UpdateBowlCommunityRequest{
        private Long id;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class UpdateBowlCommunityResponse{
        private Long id;
        private String content;
    }
}
