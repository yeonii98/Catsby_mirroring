package com.hanium.catsby.BowlCommunity.controller;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.BowlCommunity.service.BowlCommunityService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BowlCommunityController {

    private final BowlCommunityService bowlCommunityService;

    @PostMapping("/bowl-community/write")
    public CreateBowlCommunityResponse savaBowlCommunity(@RequestBody BowlCommunity bowlCommunity) {
        Long id = bowlCommunityService.savaCommunity(bowlCommunity);
        return new CreateBowlCommunityResponse(id);
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


    @PutMapping("/bowl-community/{id}")
    public UpdateBowlCommunityResponse updateBowlCommunity(@PathVariable("id") Long id, @RequestBody UpdateBowlCommunityRequest request){
        bowlCommunityService.update(id, request.getImage(), request.getContent());
        BowlCommunity findBowlCommunity = bowlCommunityService.findCommunity(id);
        return new UpdateBowlCommunityResponse(findBowlCommunity.getId(), findBowlCommunity.getImage(), findBowlCommunity.getContent());
    }


    @DeleteMapping("/bowl-community/{id}")
    public void DeleteBowlCommunity(@PathVariable("id") Long id){
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
