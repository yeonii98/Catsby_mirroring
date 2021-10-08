package com.hanium.catsby.domain.user.controller;

import com.hanium.catsby.domain.user.service.MyPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyPostController {

    @Autowired
    MyPostService myPostService;

    @GetMapping("/myPost/{uid}")
    public List myPost(@PathVariable String uid){
        return myPostService.listMyPost(uid);
    }

}
