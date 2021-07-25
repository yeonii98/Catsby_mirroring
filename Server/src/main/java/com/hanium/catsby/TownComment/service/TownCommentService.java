package com.hanium.catsby.TownComment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TownCommentService {

    @Autowired
    TownCommentRepository townRepository;

    public String currentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    @Transactional
    public void writeTownComment(int id, TownComment requestTownComment){
        com.hanium.catsby.domain.TownCommunity townCommunity = townRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
                }); //영속화 완료

        //        requestTownComment.setUser(user);
        requestTownComment.setTownCommunity(townCommunity);

        townCommentRepository.save(requestTownComment);
    }

    @Transactional
    public void deleteTownComment(int commentId){
        townCommentRepository.deleteById(commentId);
    }
}
