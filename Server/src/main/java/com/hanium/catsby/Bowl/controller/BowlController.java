package com.hanium.catsby.Bowl.controller;

import com.hanium.catsby.Bowl.domain.Bowl;
import com.hanium.catsby.Bowl.service.BowlService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BowlController {

    private final BowlService bowlService;

    @PostMapping("/bowl/enroll")
    public CreateBowlResponse savaBowl(@RequestBody Bowl bowl){
        Long id = bowlService.enroll(bowl);
        return new CreateBowlResponse(id);
    }

    @Data
    static class CreateBowlResponse{
        private Long id;

        public CreateBowlResponse(Long id) {
            this.id = id;
        }
    }

    @PutMapping("/bowl/{id}")
    public UpdateBowlResponse updateBowl(@PathVariable("id") Long id, @RequestBody UpdateBowlRequest request){
        bowlService.update(id, request.getName(), request.getInfo(), request.getAddress(), request.getImage());
        Bowl findBowl = bowlService.findOne(id);
        return new UpdateBowlResponse(findBowl.getId(), findBowl.getInfo(), findBowl.getName(), findBowl.getAddress(), findBowl.getImage());
    }

    @DeleteMapping("/bowl/{id}")
    public void DeleteBowl(@PathVariable("id") Long id){
        bowlService.delete(id);
    }

    @GetMapping("/bowls")
    public List<Bowl> bowls() {
        return bowlService.findAllBowls();
    }

    // 이미지 -> ? / 등록날짜는 수정 x
    @Data
    static class UpdateBowlRequest{
        private Long id;
        private String info;
        private String name;
        private String address;
        private byte[] image;
    }

    @Data
    @AllArgsConstructor
    static class UpdateBowlResponse{
        private Long id;
        private String info;
        private String name;
        private String address;
        private byte[] image;
    }


}
