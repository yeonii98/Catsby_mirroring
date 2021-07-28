package com.hanium.catsby.Town.service;

import com.hanium.catsby.Town.domain.TownComment;
import com.hanium.catsby.Town.domain.TownCommunity;
import com.hanium.catsby.Town.repository.TownCommentRepository;
import com.hanium.catsby.Town.repository.TownCommunityRepository;
import com.hanium.catsby.Town.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TownCommentService {

    @Autowired
    TownCommentRepository townCommentRepository;

    @Autowired
    TownCommunityRepository townCommunityRepository;

    @Autowired
    UserRepository userRepository;

    public String currentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Transactional
    public void writeTownComment(int id, TownComment requestTownComment){
        TownCommunity townCommunity = townCommunityRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
                }); //영속화 완료


        requestTownComment.setUser(userRepository.getById(2));
        requestTownComment.setTownCommunity(townCommunity);
        requestTownComment.setDate(currentTime());
        townCommentRepository.save(requestTownComment);
    }

    @Transactional
    public void deleteTownComment(int commentId){
        townCommentRepository.deleteById(commentId);
    }

    @Transactional
    public void updateTownComment(int id, TownComment requestTownComment) {//댓글 수정
        TownComment townComment = townCommentRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 찾기 실패 : 댓글 id를 찾을 수 없습니다.");
                }); //영속화 완료

        townComment.setContent(requestTownComment.getContent());
        //해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹이 일어남 - 자동 업데이트됨. db쪽으로 flush
    }
}
