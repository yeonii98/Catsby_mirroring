package com.hanium.catsby.BowlCommunity.controller;

import com.hanium.catsby.BowlCommunity.domain.BowlComment;
import com.hanium.catsby.BowlCommunity.service.BowlCommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BowlCommentController {

    private final BowlCommentService bowlCommentService;

    @PostMapping("/bowl-comment/{userId}/{communityId}")
    public CreateBowlCommentResponse saveBowlComment(@PathVariable("userId") Long userId, @PathVariable("communityId") Long communityId, @RequestBody BowlComment bowlComment){
        Long id = bowlCommentService.savaComment(bowlComment, userId, communityId);
        return new CreateBowlCommentResponse(id);
    }

    @Data
    static class CreateBowlCommentResponse{
        private Long id;

        public CreateBowlCommentResponse(Long id){
            this.id = id;
        }
    }

    @GetMapping("/bowl-comments")
    public List<BowlComment> bowlComments() {
        return bowlCommentService.findComments();
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
