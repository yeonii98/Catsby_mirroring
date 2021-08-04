package com.hanium.catsby.BowlCommunity.service;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.BowlCommunity.repository.BowlCommunityRepository;
import com.hanium.catsby.MyWriting.domain.MyPost;
import com.hanium.catsby.MyWriting.repository.MyPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlCommunityService {

    private final BowlCommunityRepository bowlCommunityRepository;

    @Autowired
    MyPostRepository myPostRepository;

    @Transactional
    public Long savaCommunity(BowlCommunity bowlCommunity) {
        bowlCommunityRepository.save(bowlCommunity);

        //myPost
        MyPost myPost = new MyPost();
        myPost.setBowlCommunity(bowlCommunity);
        myPostRepository.save(myPost);
        return bowlCommunity.getId();
    }

    @Transactional(readOnly = true)
    public List<BowlCommunity> findCommunities() {
        return bowlCommunityRepository.findAllBowlCommunity();
    }

    @Transactional(readOnly = true)
    public BowlCommunity findCommunity(Long bowlId) {
        return bowlCommunityRepository.findBowlCommunity(bowlId);
    }

    @Transactional
    public void delete(Long id) {
        //myPost
        myPostRepository.deleteByBowlCommunity_Id(id);

        bowlCommunityRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, byte[] image, String content){
        BowlCommunity bowlCommunity = bowlCommunityRepository.findBowlCommunity(id);
        bowlCommunity.setImage(image);
        bowlCommunity.setContent(content);

        //myPost
        MyPost myPost = myPostRepository.findByBowlCommunity_Id(id);
        myPost.setBowlCommunity(bowlCommunity);
    }


}
