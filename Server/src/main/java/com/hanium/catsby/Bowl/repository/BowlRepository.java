package com.hanium.catsby.Bowl.repository;

import com.hanium.catsby.Bowl.domain.Bowl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BowlRepository {

    private final EntityManager em;

    public void save(Bowl bowl){
        em.persist(bowl);
    }

    // 모든 밥그릇 조회
    public List<Bowl> findAllBowl() {
        return em.createQuery("select b from Bowl b", Bowl.class).getResultList();
    }

    public Bowl findBowl(Long id) {
        return em.find(Bowl.class, id);
    }

    public void deleteById(Long id){
        Bowl bowl = findBowl(id);
        em.remove(bowl);
    }


}
