package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.Bowl;
import com.hanium.catsby.bowl.service.BowlService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BowlController {

    private final BowlService bowlService;

    @PostMapping("/bowl/enroll")
    public CreateBowlResponse savaBowl(@RequestBody CreateBowlRequest request){

        Bowl bowl = new Bowl();
        bowl.setInfo(request.getInfo());
        bowl.setName(request.getName());
        bowl.setAddress(request.getAddress());
        bowl.setImage(request.getImage());

        Long id = bowlService.enroll(bowl);
        return new CreateBowlResponse(id);
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
    public BowlResult bowls() {
        List<Bowl> findBowls = bowlService.findAllBowls();
        List<BowlDto> collect = findBowls.stream()
                .map(b -> new BowlDto(b.getInfo(), b.getName(), b.getAddress(), b.getImage(), b.getCreatedDate()))
                .collect(Collectors.toList());
        return new BowlResult(collect);
    }


    @GetMapping("/bowls/{uid}")
    public BowlResult userBowlList(@PathVariable("uid") String uid) {
        List<Bowl> findBowls = bowlService.findUserBowls(uid);
        List<BowlDto> collect = findBowls.stream()
                .map(b -> new BowlDto(b.getInfo(), b.getName(), b.getAddress(), b.getImage(), b.getCreatedDate()))
                .collect(Collectors.toList());
        return new BowlResult(collect);
        //return findBowls;
    }

    @Data
    @AllArgsConstructor
    static class BowlResult<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class BowlDto{
        private String info;
        private String name;
        private String address;
        private byte[] image;
        private LocalDateTime createDate;
    }

    @Data
    static class CreateBowlRequest{
        private String info;
        private String name;
        private String address;
        private byte[] image;
    }

    @Data
    static class CreateBowlResponse{
        private Long id;

        public CreateBowlResponse(Long id) {
            this.id = id;
        }
    }

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
