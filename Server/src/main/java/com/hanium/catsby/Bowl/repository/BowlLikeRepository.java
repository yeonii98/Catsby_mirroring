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

    public List<BowlLike> findAllBowlLike() {
        return em.createQuery("select bl from BowlLike bl", BowlLike.class).getResultList();
    }

    public void deleteById(Long id) {
        BowlLike bowlLike = findBowlLike(id);
        em.remove(bowlLike);
    }

    public BowlLike findBowlLike(Long id) {
        return em.find(BowlLike.class, id);
    }
}
