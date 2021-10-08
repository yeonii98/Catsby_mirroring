package com.hanium.catsby.bowl.repository;

import com.hanium.catsby.bowl.domain.BowlLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BowlLikeRepository {

    private final EntityManager em;

    public void save(BowlLike bowlLike){
        em.persist(bowlLike);
    }

    public void deleteById(Long id) {
        BowlLike bowlLike = findBowlLike(id);
        em.remove(bowlLike);
    }

    public List<BowlLike> findLikeByUserId(Long userId){
        return em.createQuery("select bl from BowlLike bl" +
                " where bl.user.id = :userId", BowlLike.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public int findLikeByCommunityId(Long communityId){
        List<BowlLike> resultList = em.createQuery("select bl from BowlLike bl" +
                " where bl.bowlCommunity.id = :communityId", BowlLike.class)
                .setParameter("communityId", communityId)
                .getResultList();
        return resultList.size();
    }

    public BowlLike findBowlLike(Long id) {
        return em.find(BowlLike.class, id);
    }
}
