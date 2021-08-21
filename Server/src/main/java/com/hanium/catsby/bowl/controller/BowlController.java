package com.hanium.catsby.bowl.controller;

import com.hanium.catsby.bowl.domain.Bowl;
import com.hanium.catsby.bowl.service.BowlService;
import com.hanium.catsby.notification.exception.DuplicateBowlInfoException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BowlController {

    private final BowlService bowlService;

    @PostMapping("/bowl")
        public ResponseEntity<CreateBowlResponse> savaBowl(@RequestParam("info") String info, @RequestParam("name") String name, @RequestParam("address") String address, @RequestPart MultipartFile files){

        String fileName = files.getOriginalFilename();
        String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();

        File destinationFile;
        String destinationFileName;
        String filePath = "/";

        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
            destinationFile = new File(filePath + destinationFileName);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdir();
        try {
            files.transferTo(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Bowl bowl = new Bowl();
        bowl.setInfo(info);
        bowl.setName(name);
        bowl.setAddress(address);
        bowl.setFilename(destinationFileName);
        bowl.setPath(filePath);

        try {
            Long id = bowlService.enroll(bowl);
            return ResponseEntity.ok(new CreateBowlResponse(id));
        } catch (DuplicateBowlInfoException e) {
            return ResponseEntity.status(409).body(new CreateBowlResponse(null));
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
    }

    @PostMapping("/bowl/{uid}")
    public ResponseEntity<CreateBowlResponse> addUser(@PathVariable("uid") String uid, @RequestBody AddUserRequest request) {
        Long bowlId = bowlService.saveBowlUser(request.getBowlInfo(), uid);
        return ResponseEntity.ok(new CreateBowlResponse(bowlId));
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

    @Data
    private class AddUserRequest {
        private String bowlInfo;
    }
}
