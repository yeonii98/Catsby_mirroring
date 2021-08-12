package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.BowlLike;
import com.hanium.catsby.bowl.service.BowlLikeService;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.service.NotificationService;
import com.hanium.catsby.notification.util.NotificationUtil;
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
    private final NotificationService notificationService;

    @PostMapping("/bowl-like/{userId}/{communityId}")
<<<<<<< HEAD:Server/src/main/java/com/hanium/catsby/BowlCommunity/controller/BowlLikeController.java
    public CreateBowlLikeResponse saveBowlLike(@PathVariable("userId") Long userId, @PathVariable("communityId") Long communityId, @RequestBody CreateBowlLikeRequest request){
        BowlLike bowlLike = new BowlLike();
=======
    public CreateBowlLikeResponse saveBowlLike(@PathVariable("userId") Long userId, @PathVariable("communityId") Long communityId, @RequestBody BowlLike bowlLike) {
>>>>>>> 6b613cfe5b4d0d04ff5306cc18ff66a7f14abebf:Server/src/main/java/com/hanium/catsby/bowl/controller/BowlLikeController.java
        Long id = bowlLikeService.saveBowlLike(bowlLike, userId, communityId);

        String content = bowlLike.getBowlCommunity().getContent();
        String message = userId + NotificationUtil.makeNotification(content, NotificationType.LIKE);
        notificationService.saveNotification(bowlLike.getBowlCommunity().getUser(), message);

        return new CreateBowlLikeResponse(id);
    }

    @Data
    static class CreateBowlLikeResponse {
        private Long id;

        public CreateBowlLikeResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateBowlLikeRequest{
    }

    @GetMapping("/bowl-likes")
    public List<BowlLike> bowlLikes() {
        return bowlLikeService.findLikes();
    }

    @DeleteMapping("/bowl-like/{id}")
    public void DeleteBowlLike(@PathVariable("id") Long id) {
        bowlLikeService.delete(id);
    }

}
