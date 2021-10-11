package com.hanium.catsby.domain.bowl.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanium.catsby.domain.bowl.service.BowlCommentService;
import com.hanium.catsby.domain.bowl.model.BowlCommunity;
import com.hanium.catsby.domain.user.model.Users;


import com.hanium.catsby.domain.bowl.model.BowlComment;
import com.hanium.catsby.domain.notification.model.NotificationType;
import com.hanium.catsby.domain.notification.service.NotificationService;
import com.hanium.catsby.domain.user.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BowlCommentController {

    private final BowlCommentService bowlCommentService;
    private final NotificationService notificationService;
    private final UserService userService;

    @PostMapping("/bowl/community/comment/{uid}/{communityId}")
    public CreateBowlCommentResponse saveBowlComment(@PathVariable("uid") String uid, @PathVariable("communityId") Long communityId, @RequestBody CreateBowlCommentRequest request){
        Users user = userService.findUsersByUid(uid);
        BowlComment bowlComment = new BowlComment();
        bowlComment.setContent(request.getContent());
        bowlComment.setUid(uid);
        Long id = bowlCommentService.savaComment(bowlComment, user.getId(), communityId);

        String content = bowlComment.getBowlCommunity().getContent();
        notificationService.saveNotification(user, content, NotificationType.COMMENT);
        return new CreateBowlCommentResponse(id, user.getNickname(), bowlComment.getCreatedDate(), user.getId());
    }

    @GetMapping("/bowl/community/comments/{communityId}")
    public BowlCommentResult bowlComment(@PathVariable("communityId") Long communityId) {
        List<BowlComment> findComment = bowlCommentService.findCommentByCommunityId(communityId);
        List<BowlCommentDto> collect = findComment.stream()
                .map(bc -> new BowlCommentDto(bc.getId(), bc.getContent(), bc.getCreatedDate(), bc.getUser(), bc.getBowlCommunity(), bc.getUid()))
                .collect(Collectors.toList());
        return new BowlCommentResult(collect);
    }

    @Data
    @AllArgsConstructor
    static class BowlCommentResult<T> {
        private T data;
    }

    @PutMapping("/bowl/community/comment/{commentId}")
    public UpdateBowlCommentResponse updateBowlComment(@PathVariable("commentId") Long commentId, @RequestBody UpdateBowlCommentRequest request){
        bowlCommentService.update(commentId, request.getContent());
        BowlComment findBowlComment = bowlCommentService.findBowlComment(commentId);
        return new UpdateBowlCommentResponse(findBowlComment.getId(), findBowlComment.getContent());

    }

    @DeleteMapping("/bowl/community/comment/{commentId}")
    public void DeleteBowlComment(@PathVariable("commentId") Long id){
        bowlCommentService.delete(id);
    }

    @Data
    @AllArgsConstructor
    static class BowlCommentDto{
        private Long id;
        private String content;
        private LocalDateTime createDate;
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        private Users user;
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        private BowlCommunity bowlCommunity;
        private String uid;
    }

    @Data
    static class UpdateBowlCommentRequest{
        private Long id;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class UpdateBowlCommentResponse{
        private Long id;
        private String content;
    }

    @Data
    static class CreateBowlCommentRequest{
        private String content;
    }

    @Data
    static class CreateBowlCommentResponse{
        private Long id;
        private String nickname;
        private LocalDateTime date;
        private Long userId;

        public CreateBowlCommentResponse(Long id, String nickname, LocalDateTime date, Long userId){
            this.id = id;
            this.nickname = nickname;
            this.date = date;
            this.userId = userId;
        }
    }

}
