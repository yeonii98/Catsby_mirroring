package com.hanium.catsby.User.repository;

import com.hanium.catsby.User.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findUser(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAllUser() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

}
