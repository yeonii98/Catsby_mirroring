package com.hanium.catsby.TownCommunity.service;

import com.hanium.catsby.TownComment.repository.TownCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TownCommunityService {
    @Autowired
    TownCommentRepository townRepository;
    public String currentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Transactional
    public void writeTownCommunity(TownCommunity townCommunity) {//글 쓰기
//        townCommunity.setUser(user);
        townCommunity.setDate(currentTime());
        townRepository.save(townCommunity);
    }

    public Page<com.hanium.catsby.domain.TownCommunity> listTownCommunity(Pageable pageable){//글 목록
        return townRepository.findAll(pageable);
    }

    public com.hanium.catsby.domain.TownCommunity retrieveTownCommunity(int id) {//글 조회
        return townRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
                });
    }

    public void deleteTownCommunity(int id) {//글 삭제
        townRepository.deleteById(id);
    }

    public void updateTownCommunity(int id, TownCommunity requestTownCommunity) {//글 수정
        com.hanium.catsby.domain.TownCommunity townCommunity = townRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
                }); //영속화 완료
        townCommunity.setTitle(requestTownCommunity.getTitle());
        townCommunity.setContent(requestTownCommunity.getContent());
        //해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹이 일어남 - 자동 업데이트됨. db쪽으로 flush
    }
}
