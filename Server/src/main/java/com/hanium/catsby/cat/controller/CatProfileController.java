package com.hanium.catsby.cat.controller;

import com.hanium.catsby.cat.mapper.CatProfileMapper;
//package com.hanium.catsby.cat.controller;


import com.hanium.catsby.cat.model.CatProfile;
import com.hanium.catsby.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatProfileController {

    private CatProfileMapper mapper;

    public CatProfileController(UserService userService, CatProfileMapper mapper) {
        this.mapper = mapper;
    }

    //고양이 목록
    @GetMapping("/cat")
    public List<CatProfile> getCatProfileList() {
        return mapper.getCatProfileList();
    }

    //고양이 세부 조회
    @GetMapping("/cat/{cat_id}")
    public CatProfile getCatProfile(@PathVariable("cat_id") int cat_id){
        return mapper.getCatProfile(cat_id);
    }

    //고양이 등록
    @PostMapping("/cat/register")
    public void putCatProfile(
            @RequestParam(value = "uid", required = false) String uid,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "health", required = false) String health,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "gender", required = false) int gender,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "spayed", required = false) int spayed)
    {
        CatProfile catProfile = new CatProfile(uid, name, health, address, gender, image, content, spayed);
        mapper.insertCatProfile(uid, name,health,address,gender,image,content,spayed);

    }

    //고양이 수정
    @PutMapping("/cat/{cat_id}")
    public void postCatProfile(@PathVariable("cat_id") int cat_id,
                               @RequestParam(value="name") String name,
                               @RequestParam(value="health", required = false)  String health ,
                               @RequestParam(value="address", required = false) String address,
                               @RequestParam(value="gender", required = false) int gender,
                               @RequestParam(value="image", required = false) String image,
                               @RequestParam(value="content", required = false) String content,
                               @RequestParam(value="spayed", required = false) int spayed){

        mapper.updateCatProfile(cat_id,name,health,address,gender,image,content,spayed);
    }

    //고양이 삭제
    @DeleteMapping("/cat/{cat_id}")
    public void deleteCatProfile(@PathVariable("cat_id") int cat_id) {
        mapper.deleteCatProfile(cat_id);
    }

}
