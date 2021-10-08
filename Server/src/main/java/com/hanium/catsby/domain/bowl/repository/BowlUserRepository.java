package com.hanium.catsby.domain.bowl.repository;

import com.hanium.catsby.domain.bowl.model.BowlUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BowlUserRepository extends JpaRepository<BowlUser, Long> {

    public BowlUser findByBowlIdAndUserId(Long bowlId, Long userId);
}
