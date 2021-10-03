package com.hanium.catsby.domain.bowl.repository;

import com.hanium.catsby.domain.bowl.model.Bowl;
import com.hanium.catsby.domain.notification.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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

    public List<Bowl> findBowlByUsers(Long userId){
        return em.createQuery("select DISTINCT b from BowlUser bu" +
                " join bu.bowl b" +
                " where bu.user.id = :userId" , Bowl.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void deleteById(Long id){
        Bowl bowl = findBowl(id);
        em.remove(bowl);
    }

    public List<TokenDto> findUsersByBowlId(Long bowlId) {
        return em.createQuery(
                        "select new com.hanium.catsby.notification.domain.dto.TokenDto(u.id, u.fcmToken)" +
                                " from Bowl b" +
                                " join b.bowlUsers bu" +
                                " join bu.user u" +
                                " where bu.bowl.id = :bowlId", TokenDto.class)
                .setParameter("bowlId", bowlId)
                .getResultList();
    }

    public List<Bowl> findByBowlInfo(String info) {
        return em.createQuery(
                "select b" +
                        " from Bowl b" +
                        " where b.info = :info", Bowl.class)
                .setParameter("info", info)
                .getResultList();
    }

    public List<Bowl> findBowlsByLastFeeding() {
        LocalDateTime beforeTime = LocalDateTime.now().minusDays(1);

        return em.createQuery(
                "select b" +
                        " from Bowl b" +
                        " where b.lastFeeding <= :before" +
                        " or b.lastFeeding is null", Bowl.class)
        .setParameter("before", beforeTime)
        .getResultList();
    }
}
