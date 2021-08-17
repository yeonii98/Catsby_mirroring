package com.hanium.catsby.Town.service;

import com.hanium.catsby.user.domain.MyPost;
import com.hanium.catsby.user.repository.MyPostRepository;
import com.hanium.catsby.Town.domain.TownCommunity;
import com.hanium.catsby.Town.repository.TownCommunityRepository;

import com.hanium.catsby.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TownCommunityService {

    @Autowired
    TownCommunityRepository townCommunityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyPostRepository myPostRepository;


    public String currentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Transactional
    public void writeTownCommunity(TownCommunity townCommunity) {//글 쓰기
        townCommunity.setUser(userRepository.findUser((long) 2));
        townCommunity.setDate(currentTime());
        townCommunityRepository.save(townCommunity);

        //myPost
        MyPost myPost = new MyPost();
        myPost.setTownCommunity(townCommunity);
        myPostRepository.save(myPost);
    }

    @Transactional(readOnly = true)
    public List listTownCommunity(){//글 목록
        return townCommunityRepository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public TownCommunity retrieveTownCommunity(int id) {//글 조회
        return townCommunityRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 게시글 id를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void deleteTownCommunity(int id) {//글 삭제
        //myPost
        myPostRepository.deleteByTownCommunity_Id(id);
        townCommunityRepository.deleteById(id);
    }

    @Transactional
    public void updateTownCommunity(int id, TownCommunity requestTownCommunity) {//글 수정
        TownCommunity townCommunity = townCommunityRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 게시글 id를 찾을 수 없습니다.");
                }); //영속화 완료
        townCommunity.setTitle(requestTownCommunity.getTitle());
        townCommunity.setContent(requestTownCommunity.getContent());
        //해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹이 일어남 - 자동 업데이트됨. db쪽으로 flush

        //myPost
        MyPost myPost = myPostRepository.findByTownCommunity_Id(id);
        myPost.setTownCommunity(townCommunity);
    }
}
