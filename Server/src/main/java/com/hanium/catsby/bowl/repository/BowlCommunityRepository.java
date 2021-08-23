package com.hanium.catsby.bowl.repository;

import com.hanium.catsby.bowl.domain.Bowl;
import com.hanium.catsby.bowl.domain.BowlCommunity;
import com.hanium.catsby.bowl.domain.BowlLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Queue;

@Repository
@RequiredArgsConstructor
public class BowlCommunityRepository {

    private final EntityManager em;

    public void save(BowlCommunity bowlCommunity){
        em.persist(bowlCommunity);
    }

    public BowlCommunity findBowlCommunity(Long id){
        return em.find(BowlCommunity.class, id);
    }

    public List<BowlCommunity> findBowlCommunitiesByBowl(Long bowlId){
        return em.createQuery("select bc from BowlCommunity bc" +
                "where bc.bowl.id = :bowlId", BowlCommunity.class)
                .setParameter("bowlId", bowlId)
                .getResultList();
    }

    public List<BowlCommunity> findAllBowlCommunity() {
        return em.createQuery("select bm from BowlCommunity bm", BowlCommunity.class).getResultList();
    }

    public List<BowlCommunity> findBowlCommunityByBowl(Long userId){
        return em.createQuery("select DISTINCT(bc) from BowlCommunity bc" +
                " join bc.bowl b" +
                " join b.bowlUsers bu" +
                " where bc.user.id = :userId", BowlCommunity.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Long findBowlLikesByCommunity(Long communityId){
        return (Long) em.createQuery("select count(bl) from BowlLike bl" +
                " where bl.bowlCommunity.id = :communityId")
                .setParameter("communityId", communityId)
                .getSingleResult();
    }

    public void deleteById(Long id) {
        BowlCommunity bowlCommunity = findBowlCommunity(id);
        em.remove(bowlCommunity);
    }
}
