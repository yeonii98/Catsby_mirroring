package com.hanium.catsby.BowlComment.service;

import com.hanium.catsby.BowlLike.domain.BowlLike;
import com.hanium.catsby.BowlLike.repository.BowlLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlLikeService {

    private final BowlLikeRepository bowlLikeRepository;

    @Transactional
    public Long saveBowlLike(BowlLike bowlLike){
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
