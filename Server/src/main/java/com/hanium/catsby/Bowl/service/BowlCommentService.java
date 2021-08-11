package com.hanium.catsby.Bowl.service;

import com.hanium.catsby.Bowl.domain.BowlComment;
import com.hanium.catsby.Bowl.domain.BowlCommunity;
import com.hanium.catsby.Bowl.repository.BowlCommentRepository;
import com.hanium.catsby.Bowl.repository.BowlCommunityRepository;
import com.hanium.catsby.User.domain.MyComment;
import com.hanium.catsby.User.domain.Users;
import com.hanium.catsby.User.repository.MyCommentRepository;
import com.hanium.catsby.User.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BowlCommentService {

    private final BowlCommentRepository bowlCommentRepository;
    private final UserRepository userRepository;
    private final BowlCommunityRepository bowlCommunityRepository;

    @Autowired
    MyCommentRepository myCommentRepository;

    @Transactional
    public Long savaComment(BowlComment bowlComment, Long userId, Long communityId) {

        Users users = userRepository.findUser(userId);
        BowlCommunity bowlCommunity = bowlCommunityRepository.findBowlCommunity(communityId);
        bowlComment.setUser(users);
        bowlComment.setBowlCommunity(bowlCommunity);
        bowlComment.setCreateDate();
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
        bowlComment.setUpdateDate();
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
