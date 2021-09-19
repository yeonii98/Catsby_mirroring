package com.hanium.catsby.bowl.service;

import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.domain.BowlLike;
import com.hanium.catsby.bowl.repository.BowlCommunityRepository;
import com.hanium.catsby.bowl.repository.BowlLikeRepository;
import com.hanium.catsby.user.domain.Users;
import com.hanium.catsby.user.repository.UserRepository;
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
    public Long saveBowlLike(BowlLike bowlLike, Long userId, Long communityId){
        BowlCommunity bowlCommunity = bowlCommunityRepository.findBowlCommunity(communityId);
        Users users = userRepository.findUser(userId);
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
    public void delete(Long id) {
        bowlLikeRepository.deleteById(id);
    }
}
