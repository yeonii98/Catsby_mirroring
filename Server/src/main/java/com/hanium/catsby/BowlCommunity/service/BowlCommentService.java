package com.hanium.catsby.BowlCommunity.service;

import com.hanium.catsby.BowlCommunity.domain.BowlComment;
import com.hanium.catsby.BowlCommunity.repository.BowlCommentRepository;
import com.hanium.catsby.MyWriting.domain.MyComment;
import com.hanium.catsby.MyWriting.repository.MyCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlCommentService {

    private final BowlCommentRepository bowlCommentRepository;

    @Autowired
    MyCommentRepository myCommentRepository;

    @Transactional
    public Long savaComment(BowlComment bowlComment) {
        bowlCommentRepository.save(bowlComment);

        //myComment
        MyComment myComment = new MyComment();
        myComment.setBowlComment(bowlComment);
        myCommentRepository.save(myComment);

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

        //myComment
        MyComment myComment = myCommentRepository.findByBowlComment_Id(id);
        myComment.setBowlComment(bowlComment);
    }

    @Transactional(readOnly = true)
    public BowlComment findBowlComment(Long id){
        return bowlCommentRepository.findBowlComment(id);
    }

    @Transactional
    public void delete(Long id) {
        //myComment
        myCommentRepository.deleteByBowlComment_Id(id);

        bowlCommentRepository.deleteById(id);
    }
}
