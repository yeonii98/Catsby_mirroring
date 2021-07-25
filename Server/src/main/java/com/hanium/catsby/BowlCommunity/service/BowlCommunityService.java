package com.hanium.catsby.BowlCommunity.service;

import com.hanium.catsby.BowlCommunity.domain.BowlCommunity;
import com.hanium.catsby.BowlCommunity.repository.BowlCommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlCommunityService {

    private final BowlCommunityRepository bowlCommunityRepository;

    @Transactional
    public Long savaCommunity(BowlCommunity bowlCommunity) {
        bowlCommunityRepository.save(bowlCommunity);
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
        bowlCommunityRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, byte[] image, String content){
        BowlCommunity bowlCommunity = bowlCommunityRepository.findBowlCommunity(id);
        bowlCommunity.setImage(image);
        bowlCommunity.setContent(content);
    }


}
