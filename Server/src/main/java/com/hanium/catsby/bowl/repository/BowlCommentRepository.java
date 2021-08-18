package com.hanium.catsby.bowl.repository;

import com.hanium.catsby.bowl.domain.BowlComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BowlCommentRepository {

    private final EntityManager em;

    public void save(BowlComment bowlComment){
        em.persist(bowlComment);
    }

    public List<BowlComment> findAllBowlComment() {
        return em.createQuery("select bc from BowlComment bc", BowlComment.class).getResultList();
    }

    public BowlComment findBowlComment(Long id) {
        return em.find(BowlComment.class, id);
    }

    public List<BowlComment> findBowlCommentByCommunityId (Long communityId) {
        return em.createQuery("select bc from BowlComment bc" +
                " where bc.bowlCommunity.id = :communityId", BowlComment.class)
                .setParameter("communityId", communityId)
                .getResultList();
    }



    public void deleteById(Long id) {
        BowlComment bowlComment = findBowlComment(id);
        em.remove(bowlComment);
    }
}
