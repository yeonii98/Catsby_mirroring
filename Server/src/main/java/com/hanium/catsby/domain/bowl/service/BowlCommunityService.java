package com.hanium.catsby.domain.bowl.service;

import com.hanium.catsby.domain.bowl.model.BowlCommunity;
import com.hanium.catsby.domain.bowl.model.Bowl;
import com.hanium.catsby.domain.bowl.repository.BowlRepository;
import com.hanium.catsby.domain.bowl.repository.BowlCommunityRepository;
import com.hanium.catsby.domain.common.sevice.S3Service;
import com.hanium.catsby.domain.user.model.Users;
import com.hanium.catsby.domain.user.repository.UserRepository;
import com.hanium.catsby.domain.user.model.MyPost;
import com.hanium.catsby.domain.user.repository.MyPostRepository;
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
    private final MyPostRepository myPostRepository;

    @Transactional
    public Long savaCommunity(BowlCommunity bowlCommunity, String userId, Long bowlId) {
        Users user = userRepository.findUserByUid(userId);
        Bowl bowl = bowlRepository.findBowl(bowlId);

        bowlCommunity.setUser(user);
        bowlCommunity.setBowl(bowl);
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
    public List<BowlCommunity> findCommunitiesByUser(String userId) {
        Users user = userRepository.findUserByUid(userId);
        return bowlCommunityRepository.findBowlCommunityByBowl(user.getId());
    }


    @Transactional(readOnly = true)
    public Long findLikesByCommunity(Long communityId) {
        return bowlCommunityRepository.findBowlLikesByCommunity(communityId);
    }


    @Transactional(readOnly = true)
    public BowlCommunity findCommunity(Long id) {
        return bowlCommunityRepository.findBowlCommunity(id);
    }

    @Transactional(readOnly = true)
    public List<BowlCommunity> findCommunityByBowl(Long bowlId) {
        return bowlCommunityRepository.findBowlCommunitiesByBowl(bowlId);
    }

    @Transactional
    public void delete(Long id) {
        bowlCommunityRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String content){
        BowlCommunity bowlCommunity = bowlCommunityRepository.findBowlCommunity(id);
        bowlCommunity.setContent(content);
    }
}
