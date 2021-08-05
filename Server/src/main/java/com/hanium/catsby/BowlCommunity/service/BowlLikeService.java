package com.hanium.catsby.BowlCommunity.service;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.BowlCommunity.domain.BowlLike;
import com.hanium.catsby.BowlCommunity.repository.BowlCommunityRepository;
import com.hanium.catsby.BowlCommunity.repository.BowlLikeRepository;
import com.hanium.catsby.User.domain.Users;
import com.hanium.catsby.User.repository.UserRepository;
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
        bowlLike.setCreateDate();
        bowlLikeRepository.save(bowlLike);
        return bowlLike.getId();
    }

    @Transactional(readOnly = true)
    public List<BowlLike> findLikes() {
        return bowlLikeRepository.findAllBowlLike();
    }

    @Transactional
    public void delete(Long id) {
        bowlLikeRepository.deleteById(id);
    }
}
