package com.hanium.catsby.domain.user.service;

import com.hanium.catsby.domain.user.repository.MyPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyPostService {

    @Autowired
    MyPostRepository myPostRepository;

    @Transactional(readOnly = true)
    public List listMyPost(String user_uid){
        return myPostRepository.findByTownCommunity_User_UidOrBowlCommunity_User_UidOrderByMyPostIdDesc(user_uid,user_uid);
    }

}
