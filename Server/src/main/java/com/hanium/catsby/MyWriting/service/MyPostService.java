package com.hanium.catsby.MyWriting.service;

import com.hanium.catsby.MyWriting.repository.MyPostRepository;
import com.hanium.catsby.User.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyPostService {

    @Autowired
    MyPostRepository myPostRepository;

    @Transactional(readOnly = true)
    public List listMyPost(Long user_id){
        return myPostRepository.findByTownCommunity_User_IdOrBowlCommunity_User_IdOrderByMyPostIdDesc(user_id,user_id);
    }

}
