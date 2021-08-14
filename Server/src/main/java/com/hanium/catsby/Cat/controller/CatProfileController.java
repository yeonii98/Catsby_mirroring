package com.hanium.catsby.Cat.controller;

import com.hanium.catsby.Cat.mapper.CatProfileMapper;
import com.hanium.catsby.cat.model.CatProfile;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CatProfileController {

    private CatProfileMapper mapper;

    //private Map<Integer, CatProfile> catMap;

    public CatProfileController(CatProfileMapper mapper) {
        this.mapper = mapper;
    }

    /*
    @PostConstruct
    public void init(){
        catMap = new HashMap<Integer, CatProfile>();
        catMap.put(1, new CatProfile(1,"고양이1", "건강",
                "은평구", Boolean.TRUE, null, "잘 지내는 중",  Boolean.TRUE));
        catMap.put(2, new CatProfile(2,"고양이2", "건강하지 않음",
                "강서구", Boolean.FALSE, null, "그럭저럭",  Boolean.TRUE));

    }

     */

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
            @RequestParam(value = "name") String name,
            @RequestParam(value = "health", required = false) String health,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "gender", required = false) boolean gender,
            @RequestParam(value = "image", required = false) Blob image,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "spayed", required = false) boolean spayed)
    {
        CatProfile catProfile = new CatProfile(name, health, address, gender, image, content, spayed);
        mapper.insertCatProfile(name,health,address,gender,image,content,spayed);

    }

    //고양이 수정
    @PutMapping("/cat/{cat_id}")
    public void postCatProfile(@PathVariable("cat_id") int cat_id,
                               @RequestParam(value="name") String name,
                               @RequestParam(value="health", required = false) String health ,
                               @RequestParam(value="address", required = false) String address,
                               @RequestParam(value="gender", required = false) boolean gender,
                               @RequestParam(value="image", required = false) Blob image,
                               @RequestParam(value="content", required = false) String content,
                               @RequestParam(value="spayed", required = false) boolean spayed){

        mapper.updateCatProfile(cat_id,name,health,address,gender,image,content,spayed);
    }

    //고양이 삭제
    @DeleteMapping("/cat/{cat_id}")
    public void deleteCatProfile(@PathVariable("cat_id") int cat_id) {
        mapper.deleteCatProfile(cat_id);
    }

}
