package com.hanium.catsby.BowlCommunity.service;

import com.hanium.catsby.BowlCommunity.domain.BowlComment;
import com.hanium.catsby.BowlCommunity.repository.BowlCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlCommentService {

    private final BowlCommentRepository bowlCommentRepository;

    @Transactional
    public Long savaComment(BowlComment bowlComment) {
        bowlCommentRepository.save(bowlComment);
        return bowlComment.getId();
    }

    @Transactional(readOnly = true)
    public List<BowlComment> findComments() {
        return bowlCommentRepository.findAllBowlComment();
    }

    @Transactional
    public void update(Long id, String content) {
        BowlComment bowlComment = bowlCommentRepository.findBowlComment(id);
        bowlComment.setContent(content);
    }

    @Transactional(readOnly = true)
    public BowlComment findBowlComment(Long id){
        return bowlCommentRepository.findBowlComment(id);
    }

    @Transactional
    public void delete(Long id) {
        bowlCommentRepository.deleteById(id);
    }
}
