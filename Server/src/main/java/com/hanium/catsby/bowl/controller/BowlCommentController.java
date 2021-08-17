package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.BowlComment;
import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.service.BowlCommentService;
import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.user.domain.Users;


import com.hanium.catsby.bowl.domain.BowlComment;
import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.service.BowlCommentService;
import com.hanium.catsby.notification.domain.NotificationType;
import com.hanium.catsby.notification.service.NotificationService;

//import com.hanium.catsby.notification.util.NotificationUtil;


import com.hanium.catsby.user.domain.Users;
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

    @PostMapping("/bowl-comment/{userId}/{communityId}")
    public CreateBowlCommentResponse saveBowlComment(@PathVariable("userId") Long userId, @PathVariable("communityId") Long communityId, @RequestBody CreateBowlCommentRequest request){

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

    @PutMapping("/bowl-comment/{id}")
    public UpdateBowlCommentResponse updateBowlComment(@PathVariable("id") Long id, @RequestBody UpdateBowlCommentRequest request){
        bowlCommentService.update(id, request.getContent());
        BowlComment findBowlComment = bowlCommentService.findBowlComment(id);
        return new UpdateBowlCommentResponse(findBowlComment.getId(), findBowlComment.getContent());

    }

    @DeleteMapping("/bowl-comment/{id}")
    public void DeleteBowlComment(@PathVariable("id") Long id){
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
