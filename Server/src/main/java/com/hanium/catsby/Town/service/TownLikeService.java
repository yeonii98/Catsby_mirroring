package com.hanium.catsby.TownLike.service;

import com.hanium.catsby.TownLike.repository.TownLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TownLikeService {

    @Autowired
    TownLikeRepository townRepository;

    public String currentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Transactional
    public void createTownLike(int id, TownLike requestTownLike){
        com.hanium.catsby.domain.TownCommunity townCommunity = townRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
                }); //영속화 완료

        //        requestTownComment.setUser(user);
        requestTownLike.setTownCommunity(townCommunity);

        townLikeRepository.save(requestTownLike);
    }

    @Transactional
    public void deleteTownLike(int townLike_id){
        townCommentRepository.deleteById(townLike_id);
    }
}
