package com.hanium.catsby.domain.user.controller;

import com.hanium.catsby.domain.user.service.MyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyCommentController {

    @Autowired
    MyCommentService myCommentService;

    @GetMapping("/myComment/{uid}")
    public List myPost(@PathVariable String uid){
        return myCommentService.listMyComment(uid);
    }
}
