package com.hanium.catsby.bowl.repository;

import com.hanium.catsby.bowl.domain.BowlCommunity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<BowlCommunity> findAllBowlCommunity() {
        return em.createQuery("select bm from BowlCommunity bm", BowlCommunity.class).getResultList();
    }

    public void deleteById(Long id) {
        BowlCommunity bowlCommunity = findBowlCommunity(id);
        em.remove(bowlCommunity);
    }
}
