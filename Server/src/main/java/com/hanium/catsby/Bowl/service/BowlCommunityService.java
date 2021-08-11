package com.hanium.catsby.Bowl.service;

import com.hanium.catsby.Bowl.domain.Bowl;
import com.hanium.catsby.Bowl.repository.BowlRepository;
import com.hanium.catsby.Bowl.domain.BowlCommunity;
import com.hanium.catsby.Bowl.repository.BowlCommunityRepository;
import com.hanium.catsby.User.domain.Users;
import com.hanium.catsby.User.repository.UserRepository;
import com.hanium.catsby.User.domain.MyPost;
import com.hanium.catsby.User.repository.MyPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlCommunityService {

    private final BowlCommunityRepository bowlCommunityRepository;
    private final UserRepository userRepository;
    private final BowlRepository bowlRepository;

    @Autowired
    MyPostRepository myPostRepository;

    @Transactional
    public Long savaCommunity(BowlCommunity bowlCommunity, Long userId, Long bowlId) {

        Users users = userRepository.findUser(userId);
        Bowl bowl = bowlRepository.findBowl(bowlId);

        bowlCommunity.setUser(users);
        bowlCommunity.setBowl(bowl);
        bowlCommunity.setCreateDate();

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
        bowlCommunity.setUpdateDate();
        //myPost
        MyPost myPost = myPostRepository.findByBowlCommunity_Id(id);
        myPost.setBowlCommunity(bowlCommunity);

    }
}
