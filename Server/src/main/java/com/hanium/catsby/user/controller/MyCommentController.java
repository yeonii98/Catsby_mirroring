package com.hanium.catsby.user.controller;

import com.hanium.catsby.user.service.MyCommentService;
import com.hanium.catsby.user.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyCommentController {

    @Autowired
    MyCommentService myCommentService;

    @GetMapping("/myComment")
    public List myPost(Users user){
        return myCommentService.listMyComment((long) 2);
    }

}
