package com.hanium.catsby.domain.bowl.service;

import com.hanium.catsby.domain.bowl.model.BowlCommunity;
import com.hanium.catsby.domain.bowl.model.BowlLike;
import com.hanium.catsby.domain.bowl.repository.BowlCommunityRepository;
import com.hanium.catsby.domain.bowl.repository.BowlLikeRepository;
import com.hanium.catsby.domain.user.model.Users;
import com.hanium.catsby.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlLikeService {

    private final BowlLikeRepository bowlLikeRepository;
    private final BowlCommunityRepository bowlCommunityRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long saveBowlLike(BowlLike bowlLike, String uid, Long userId, Long communityId){
        BowlCommunity bowlCommunity = bowlCommunityRepository.findBowlCommunity(communityId);
        bowlCommunity.setLikeCount(bowlCommunity.getLikeCount()+1);
        Users users = userRepository.findUser(userId);
        bowlLike.setUid(uid);
        bowlLike.setUser(users);
        bowlLike.setBowlCommunity(bowlCommunity);
        bowlLikeRepository.save(bowlLike);
        return bowlLike.getId();
    }

    @Transactional(readOnly = true)
    public List<BowlLike> findLikes(Long userId){
        return bowlLikeRepository.findLikeByUserId(userId);
    }

    @Transactional(readOnly = true)
    public int findLikesByCommunity(Long communityId){
        return bowlLikeRepository.findLikeByCommunityId(communityId);
    }


    @Transactional
    public void delete(Long communityId, Long id) {
        BowlCommunity bowlCommunity = bowlCommunityRepository.findBowlCommunity(communityId);
        bowlCommunity.setLikeCount(bowlCommunity.getLikeCount()-1);
        bowlLikeRepository.deleteById(id);
    }
}
