
package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.domain.BowlLike;
import com.hanium.catsby.bowl.service.BowlLikeService;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.service.NotificationService;
import com.hanium.catsby.user.service.UserService;
import com.hanium.catsby.util.NotificationUtil;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Table(name = "Bowl_Like")
public class BowlLikeController {

    private final BowlLikeService bowlLikeService;
    private final UserService userService;

    @PostMapping("/bowl-like/{uid}/{communityId}")
    public CreateBowlLikeResponse saveBowlLike(@PathVariable("uid") String uid, @PathVariable("communityId") Long communityId){
        Long userId = userService.findUserByUid(uid);
        BowlLike bowlLike = new BowlLike();
        Long id = bowlLikeService.saveBowlLike(bowlLike, userId, communityId);
        return new CreateBowlLikeResponse(id);
    }

    @Data
    static class CreateBowlLikeResponse{
        private Long id;

        public CreateBowlLikeResponse(Long id){
            this.id = id;
        }
    }

    @GetMapping("/bowl-likes/{uid}")
    public List<BowlLike> bowlLKikeByUid(@PathVariable("uid") String uid) {
        Long userId = userService.findUserByUid(uid);
        List<BowlLike> findBowlLike = bowlLikeService.findLikes(userId);
        return findBowlLike;
    }

    @DeleteMapping("/bowl-like/{id}")
    public void DeleteBowlLike(@PathVariable("id") Long id){
        bowlLikeService.delete(id);
    }

}