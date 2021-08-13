package com.hanium.catsby.user.repository;

import com.hanium.catsby.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(Users user) {
        em.persist(user);
    }

    public Users findUser(Long id) {
        return em.find(Users.class, id);
    }

    public Users findUserByUid(String uid) {
        return em.createQuery("select u from Users u where u.uid = :uid", Users.class)
                .setParameter("uid", uid)
                .getSingleResult();
    }

    public List<Users> findAllUser() {
        return em.createQuery("select u from Users u", Users.class).getResultList();
    }

}
