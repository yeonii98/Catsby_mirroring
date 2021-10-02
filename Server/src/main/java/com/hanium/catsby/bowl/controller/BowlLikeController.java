
package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.domain.BowlLike;
import com.hanium.catsby.bowl.service.BowlLikeService;
import com.hanium.catsby.user.service.UserService;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Table(name = "Bowl_Like")
public class BowlLikeController {

    private final BowlLikeService bowlLikeService;
    private final UserService userService;

    @PostMapping("/bowl/community/like/{uid}/{communityId}")
    public CreateBowlLikeResponse saveBowlLike(@PathVariable("uid") String uid, @PathVariable("communityId") Long communityId){
        Long userId = userService.findUserByUid(uid);
        BowlLike bowlLike = new BowlLike();
        Long id = bowlLikeService.saveBowlLike(bowlLike, uid, userId, communityId);
        return new CreateBowlLikeResponse(id);
    }

    @Data
    static class CreateBowlLikeResponse{
        private Long id;
        public CreateBowlLikeResponse(Long id){
            this.id = id;
        }
    }

    @GetMapping("/bowl/community/likes/{uid}")
    public BowlLikeResult bowlLKikeByUid(@PathVariable("uid") String uid) {
        Long userId = userService.findUserByUid(uid);
        List<BowlLike> findBowlLike = bowlLikeService.findLikes(userId);
        List<BowlLikeDto> collect = findBowlLike.stream()
                .map(bl -> new BowlLikeDto(bl.getId(), bl.getBowlCommunity().getId(), bl.getUid(), bl.getCreatedDate()))
                .collect(Collectors.toList());
        return new BowlLikeResult(collect);
    }

    @DeleteMapping("/bowl/community/{communityId}/{id}")
    public void DeleteBowlLike(@PathVariable("communityId") Long communityId, @PathVariable("id") Long id){
        bowlLikeService.delete(communityId, id);
    }

    @Data
    @AllArgsConstructor
    static class BowlLikeDto{
        private Long id;
        private Long bowlCommunityId;
        private String uid;
        private LocalDateTime createdDate;
    }

    @Data
    @AllArgsConstructor
    static class BowlLikeResult<T> {
        private T data;
    }

}