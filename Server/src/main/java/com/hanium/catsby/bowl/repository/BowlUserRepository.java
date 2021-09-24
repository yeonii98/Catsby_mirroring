package com.hanium.catsby.bowl.repository;

import com.hanium.catsby.bowl.domain.Bowl;
import com.hanium.catsby.bowl.domain.BowlUser;
import com.hanium.catsby.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BowlUserRepository extends JpaRepository<BowlUser, Long> {

    public BowlUser findByBowlIdAndUserId(Long bowlId, Long userId);
}
