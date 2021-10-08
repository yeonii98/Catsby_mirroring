package com.hanium.catsby.user.service;

import com.hanium.catsby.user.repository.MyCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyCommentService {

    @Autowired
    MyCommentRepository myCommentRepository;

    @Transactional(readOnly = true)
    public List listMyComment(String user_uid){
        return myCommentRepository.findByTownComment_User_UidOrBowlComment_User_UidOrderByMyCommentIdDesc(user_uid, user_uid);
    }

}
