package com.hanium.catsby.BowlLike.controller;

import com.hanium.catsby.BowlLike.domain.BowlLike;
import com.hanium.catsby.BowlComment.service.BowlLikeService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BowlLikeController {

    private final BowlLikeService bowlLikeService;

    @PostMapping("/bowl-like/like")
    public CreateBowlLikeResponse saveBowlLike(@RequestBody BowlLike bowlLike){
        Long id = bowlLikeService.saveBowlLike(bowlLike);
        return new CreateBowlLikeResponse(id);
    }

    @Data
    static class CreateBowlLikeResponse{
        private Long id;

        public CreateBowlLikeResponse(Long id){
            this.id = id;
        }
    }

    @GetMapping("/bowl-likes")
    public List<BowlLike> bowlLikes() {
        return bowlLikeService.findLikes();
    }

    @DeleteMapping("/bowl-like/{id}")
    public void DeleteBowlLike(@PathVariable("id") Long id){
        bowlLikeService.delete(id);
    }

}
