package com.hanium.catsby.BowlCommunity.controller;

import com.hanium.catsby.BowlCommunity.domain.BowlComment;
import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.BowlCommunity.service.BowlCommentService;
import com.hanium.catsby.User.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.sql.DataSourceDefinition;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BowlCommentController {

    private final BowlCommentService bowlCommentService;

    @PostMapping("/bowl-comment/{userId}/{communityId}")
    public CreateBowlCommentResponse saveBowlComment(@PathVariable("userId") Long userId, @PathVariable("communityId") Long communityId, @RequestBody CreateBowlCommentRequest request){

        BowlComment bowlComment = new BowlComment();
        bowlComment.setContent(request.getContent());
        Long id = bowlCommentService.savaComment(bowlComment, userId, communityId);

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
        List<BowlCommentDto> collect = findcomments.stream().map(c -> new BowlCommentDto(c.getContent(), c.getCreateDate(), c.getUser(), c.getBowlCommunity()))
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
