package com.hanium.catsby.user.controller;

import com.hanium.catsby.user.service.MyPostService;
import com.hanium.catsby.user.domain.Users;
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
