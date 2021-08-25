package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.service.BowlCommentService;
import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.user.domain.Users;


import com.hanium.catsby.bowl.domain.BowlComment;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.service.NotificationService;
import com.hanium.catsby.user.service.UserService;
import com.hanium.catsby.util.NotificationUtil;

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

    @PostMapping("/bowl-comment/{uid}/{communityId}")
    public CreateBowlCommentResponse saveBowlComment(@PathVariable("uid") String uid, @PathVariable("communityId") Long communityId, @RequestBody CreateBowlCommentRequest request){
        Long userId = userService.findUserByUid(uid);
        BowlComment bowlComment = new BowlComment();
        bowlComment.setContent(request.getContent());

        Long id = bowlCommentService.savaComment(bowlComment, userId, communityId);
        String content = bowlComment.getBowlCommunity().getContent();
        String message = userId + NotificationUtil.makeNotification(content, NotificationType.COMMENT);
        notificationService.saveNotification(bowlComment.getBowlCommunity().getUser(), message);

        return new CreateBowlCommentResponse(id);
    }

    @Data
    static class CreateBowlCommentRequest{
        private String content;
    }

    @Data
    static class CreateBowlCommentResponse{
        private Long id;

        public CreateBowlCommentResponse(Long id){
            this.id = id;
        }
    }

    @GetMapping("/bowl-comments")
    public BowlCommentResult bowlComments() {
        List<BowlComment> findcomments = bowlCommentService.findComments();
        List<BowlCommentDto> collect = findcomments.stream().map(c -> new BowlCommentDto(c.getContent(), c.getCreatedDate(), c.getUser(), c.getBowlCommunity()))
                .collect(Collectors.toList());
        return new BowlCommentResult(collect);
    }

    @GetMapping("/bowl-comments/{communityId}")
    public List<BowlComment> bowlComment(@PathVariable("communityId") Long communityId) {
        List<BowlComment> findComment = bowlCommentService.findCommentByCommunityId(communityId);
        return findComment;
    }

    @Data
    @AllArgsConstructor
    static class BowlCommentResult<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class BowlCommentDto{
        private String content;
        private LocalDateTime createDate;
        private Users user;
        private BowlCommunity bowlCommunity;
    }

    @PutMapping("/bowl-comment/{commentId}")
    public UpdateBowlCommentResponse updateBowlComment(@PathVariable("commentId") Long commentId, @RequestBody UpdateBowlCommentRequest request){
        bowlCommentService.update(commentId, request.getContent());
        BowlComment findBowlComment = bowlCommentService.findBowlComment(commentId);
        return new UpdateBowlCommentResponse(findBowlComment.getId(), findBowlComment.getContent());

    }

    @DeleteMapping("/bowl-comment/{commentId}")
    public void DeleteBowlComment(@PathVariable("commentId") Long id){
        bowlCommentService.delete(id);
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

}
