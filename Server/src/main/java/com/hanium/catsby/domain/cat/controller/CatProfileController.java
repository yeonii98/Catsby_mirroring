package com.hanium.catsby.domain.cat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.catsby.domain.cat.dto.CatInfo;
import com.hanium.catsby.domain.cat.mapper.CatProfileMapper;
//package com.hanium.catsby.cat.controller;


import com.hanium.catsby.domain.cat.model.CatProfile;
import com.hanium.catsby.domain.common.sevice.S3Service;
import com.hanium.catsby.domain.user.model.Users;
import com.hanium.catsby.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatProfileController {

    private final static String USER_DIR_NAME = "image/cat/";

    private final CatProfileMapper mapper;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    private ObjectMapper objectMapper = new ObjectMapper();

    //고양이 목록
    @GetMapping("/cat")
    public List<CatProfile> getCatProfileList() {
        return mapper.getCatProfileList();
    }

    //고양이 세부 조회
    @GetMapping("/cat/{cat_id}")
    public CatProfile getCatProfile(@PathVariable("cat_id") int cat_id) {
        return mapper.getCatProfile(cat_id);
    }

    //고양이 등록
    @PostMapping("/cat/register/{uid}")
    public void putCatProfile(@PathVariable("uid") String uid, @RequestPart CatInfo catInfo, @RequestPart MultipartFile file) {

        String imgUrl = s3Service.upload(file, USER_DIR_NAME, uid);
        Users user = userRepository.findUserByUid(uid);
        mapper.insertCatProfile(user.getId(), user.getAddress(), catInfo.getName(), catInfo.getHealth(), catInfo.getAddress(), catInfo.getGender(), imgUrl, catInfo.getContent(), catInfo.getSpayed());
    }

    //고양이 수정
    @PutMapping("/cat/{cat_id}")
    public void postCatProfile(@PathVariable("cat_id") int cat_id,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "health", required = false) String health,
                               @RequestParam(value = "address", required = false) String address,
                               @RequestParam(value = "gender", required = false) int gender,
                               @RequestParam(value = "image", required = false) String image,
                               @RequestParam(value = "content", required = false) String content,
                               @RequestParam(value = "spayed", required = false) int spayed) {

        mapper.updateCatProfile(cat_id, name, health, address, gender, image, content, spayed);
    }

    //고양이 삭제
    @DeleteMapping("/cat/{cat_id}")
    public void deleteCatProfile(@PathVariable("cat_id") int cat_id) {
        mapper.deleteCatProfile(cat_id);
    }

}
