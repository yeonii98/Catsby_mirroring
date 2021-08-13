package com.hanium.catsby.town.service;

import com.hanium.catsby.town.domain.TownCommunity;
import com.hanium.catsby.town.domain.TownLike;
import com.hanium.catsby.town.repository.TownCommunityRepository;
import com.hanium.catsby.town.repository.TownLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TownLikeService {

    @Autowired
    TownLikeRepository townLikeRepository;

    @Autowired
    TownCommunityRepository townCommunityRepository;

//    @Autowired
//    UserRepository userRepository;

    public String currentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Transactional
    public void createTownLike(int id, TownLike requestTownLike){
        TownCommunity townCommunity = townCommunityRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("좋아요 실패 : 게시글 id를 찾을 수 없습니다.");
                }); //영속화 완료

        requestTownLike.setTownCommunity(townCommunity);
        //requestTownLike.setUser(userRepository.getById(2));
        townLikeRepository.save(requestTownLike);
    }

    @Transactional
    public void deleteTownLike(int townLike_id){
        townLikeRepository.deleteById(townLike_id);
    }
}
