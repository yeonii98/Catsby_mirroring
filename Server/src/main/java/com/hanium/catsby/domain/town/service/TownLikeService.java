package com.hanium.catsby.domain.town.service;

import com.hanium.catsby.domain.town.model.TownCommunity;
import com.hanium.catsby.domain.town.model.TownLike;
import com.hanium.catsby.domain.town.repository.TownCommunityRepository;
import com.hanium.catsby.domain.town.repository.TownLikeRepository;
import com.hanium.catsby.domain.user.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    public String currentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Transactional
    public void createTownLike(int id, String uid, TownLike requestTownLike){
        TownCommunity townCommunity = townCommunityRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("좋아요 실패 : 게시글 id를 찾을 수 없습니다.");
                }); //영속화 완료

        requestTownLike.setTownCommunity(townCommunity);
        requestTownLike.setUser(userRepository.findUserByUid(uid));
        townLikeRepository.save(requestTownLike);
    }

    @Transactional
    public void deleteTownLike(int id, String uid){
        townLikeRepository.deleteByTownCommunity_IdAndUser_Uid(id, uid);
    }

    public int isPresent(int id, String uid){
        return townLikeRepository.countByTownCommunity_IdAndUser_Uid(id,uid);
    }
}
